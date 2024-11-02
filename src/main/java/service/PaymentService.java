package service;

import java.util.*;

import com.paypal.api.payments.*;
import com.paypal.base.rest.*;
import javax.servlet.http.HttpServletRequest;
import modelo.Cliente;
import modelo.Venta;
import utils.Constantes;
import utils.Utileria;

public class PaymentService {

    private String clientID = Constantes.clientID;
    private String clientSecret = Constantes.clientSecret;
    private String mode = Constantes.mode;
    private String moneda = Constantes.moneda;
    private String pagCancelar = "Carrito.jsp";
    private String pagExito = "Transacion?accion=procesar";

    public String authorizePayment(Venta orderDetail, HttpServletRequest request)
            throws PayPalRESTException {

        Payer payer = getPayerInformation(orderDetail.getCliente());
        RedirectUrls redirectUrls = getRedirectURLs(request);
        List<Transaction> listTransaction = getTransactionInformation(orderDetail);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");

        APIContext apiContext = new APIContext(clientID, clientSecret, mode);

        Payment approvedPayment = requestPayment.create(apiContext);

        System.out.println("=== PAGO CREADO: ====");
        System.out.println(approvedPayment);

        return getApprovalLink(approvedPayment);

    }

    private Payer getPayerInformation(Cliente obj) {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName(obj.getNombres())
                .setLastName(obj.getApellidos())
                .setEmail(obj.getCorreo());

        payer.setPayerInfo(payerInfo);

        return payer;
    }

    private RedirectUrls getRedirectURLs(HttpServletRequest request) {
        RedirectUrls redirectUrls = new RedirectUrls();

        String urlBase = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();

        redirectUrls.setCancelUrl(urlBase + "/" + pagCancelar);
        redirectUrls.setReturnUrl(urlBase + "/" + pagExito);

        return redirectUrls;
    }

    private List<Transaction> getTransactionInformation(Venta orderDetail) {

        Details details = new Details();
        details.setShipping(String.valueOf(0)); // Costo Envio
        details.setSubtotal("" + Utileria.Redondear(orderDetail.getTotal()));
        //     details.setSubtotal(String.format("%.2f", orderDetail.getTotal()));
        details.setTax(String.valueOf(0)); // Impuesto

        Amount amount = new Amount();
        amount.setCurrency(moneda);
        amount.setTotal("" + Utileria.Redondear(orderDetail.getTotal()));
        //   amount.setTotal(String.format("%.2f", orderDetail.getTotal()));
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("Productos Vendidos Paypal");

       /*
         ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        for (DetalleVenta d : orderDetail.getDetalles()) {
            Item item = new Item();
            item.setCurrency(moneda);
            item.setName(d.getProducto().getNombre());
            item.setPrice("" + d.getProducto().PrecioCnDesc());
            //   item.setPrice(String.format("%.2f",d.getProducto().PrecioCnDesc()));
            item.setQuantity("" + d.getCantidad());
            items.add(item);
        }

        itemList.setItems(items);
        transaction.setItemList(itemList);
        */

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);

        APIContext apiContext = new APIContext(clientID, clientSecret, mode);

        return payment.execute(apiContext, paymentExecution);
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(clientID, clientSecret, mode);
        return Payment.get(apiContext, paymentId);
    }
}
