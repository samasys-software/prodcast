package com.primeforce.prodcast.messaging;

import com.primeforce.prodcast.businessobjects.*;
import com.primeforce.prodcast.businessobjects.Collection;
import com.primeforce.prodcast.dao.DatabaseManager;
import com.primeforce.prodcast.businessobjects.Distributor;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 * Created by Nandhini on 8/31/2016.
 */
public class OrderDataProvider implements MessagingDataProvider {
    private long billNo;
    private int type=0;
    private Order order;
    private float amountPaid;
    private long employeeId;
    double subTotal;
    double totalSubTotal=0.0;
       double totalTax=0.0;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getSmsPhoneNumber() {
        return smsPhoneNumber;
    }

    public void setSmsPhoneNumber(String smsPhoneNumber) {
        this.smsPhoneNumber = smsPhoneNumber;
    }

    private String smsPhoneNumber;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String subject;

    public void setBillNo(long billNo) {
        this.billNo = billNo;
    }
    public Order getOrder() {
        return order;
    }



    public String[] getData(DatabaseManager databaseManager){
        String message="";
        String message1="";
        String message2="";
        String greetingMessage="";
        order=databaseManager.fetchOrder(billNo,employeeId);

        Distributor distributor = order.getDistributor();
        Employee employee = order.getEmployee();
        Customer orderCustomer = order.getCustomer();
        NumberFormat formatter = new DecimalFormat("#0.00");
        String currencySymbol=distributor.getCurrencySymbol();
        String customerName = orderCustomer.getCustomerName();
        System.out.println("currencySymbol"+currencySymbol);
        if( customerName==null || customerName.trim().length() == 0 ){
            customerName = orderCustomer.getFirstname()+" "+orderCustomer.getLastname();
        }


         if(type==0) {
             subject = order.getDistributorName() + " - New Order# " + billNo + " for " + currencySymbol + formatter.format(order.getTotalAmount()) + " from PRODCAST.com You have Paid " +currencySymbol + formatter.format(getAmountPaid()) + " And Outstanding Balance is " +currencySymbol+ formatter.format(order.getOutstandingBalance());
             greetingMessage = "Thank You For Your Order";
         }
         else if(type==1) {
             subject = order.getDistributorName() + " Payment from " + orderCustomer.getCustomerName() + " for " +currencySymbol+ formatter.format(getAmountPaid()) + ", Outstanding Balance :" +currencySymbol + formatter.format(order.getOutstandingBalance()) + " Order#" + billNo;
             greetingMessage = "Thank You For Your Payment";
         }
            else if(type==2) {
             subject = order.getDistributorName() + " Order #" + billNo + " Product Returned Total :" + currencySymbol + formatter.format(order.getTotalAmount()) + " Outstanding Balance :" + currencySymbol + formatter.format(order.getOutstandingBalance());
             greetingMessage="Thank You For Returning Your Order";
         }
        else if( type == 3 ){
             subject = order.getDistributorName()+" Order #"+billNo+" has been fulfilled";
             greetingMessage = "Your order is fulfilled and ready for pickup";
         }
        if(orderCustomer.isSmsAllowed())
        {
            smsPhoneNumber = order.getCountryCode()+orderCustomer.getCellPhone();
        }
        for(int i =0;i<order.getOrderEntries().size();i++){
            OrderEntry entry = order.getOrderEntries().get(i);
            subTotal=entry.getUnitPrice()*entry.getQuantity();
            message+="<tr>" +
                    "<td>"+entry.getQuantity()+"</td>" +
                    "<td>"+entry.getProductName()+"</td>" +
                   // "<td>"+entry.getUnitPrice()+"</td>" +

                   /* "<div class='tbl-cols'>"+entry.getAmount()+"</div>" +
                    "<div class='tbl-cols'>"+entry.getSalesTax()+"</div>" +
                    "<div class='tbl-cols'>"+entry.getOtherTax()+"</div>" +*/
                    "<td>"+formatter.format(subTotal)+"</td>" +
                    "</tr>" ;
          totalSubTotal=totalSubTotal+subTotal;
            totalTax=totalTax+(entry.getSalesTax()+entry.getOtherTax());
            System.out.println("Printed Extra Div");
        }
      /*  for(int i =0;i<order.getReturnEntries().size();i++){
            OrderEntry entry1 = order.getReturnEntries().get(i);
            message1+="<tr>" +
                    "<td>"+(i+1)+"</td>" +
                    "<td>"+entry1.getProductName()+"</td>" +
                    "<td>"+entry1.getUnitPrice()+"</td>" +
                    "<td>"+entry1.getQuantity()+"</td>" +
                    "<td>"+entry1.getAmount()+"</td>" +
                    "<td>"+entry1.getSalesTax()+"</td>" +
                    "<td>"+entry1.getOtherTax()+"</td>" +
                    "<td>"+entry1.getSubtotal()+"</td>" +
                    "<td> Hellohbhjdgsbvhjdashjvdfasvgbdfahju</td>"+
                    "</tr>" ;
        }*/
        for(int i =0;i<order.getCollectionEntries().size();i++){
            Collection collection = order.getCollectionEntries().get(i);
            message2+="<tr>" +
                    "<td>"+collection.getAmountPaid()+"</td>" +
                    "<td>"+collection.getPaymentDate()+"</td>"+
                     "</tr>";
        }

        String distributorAddress = distributor.getAddress1()+" "+distributor.getAddress2()+" "+distributor.getAddress3();
        String distributorCityState = distributor.getCity()+" , "+distributor.getState()+" "+distributor.getPostalCode();
        String customerAddress = orderCustomer.getBillingAddress1()+ " "+orderCustomer.getBillingAddress2()+" "+orderCustomer.getBillingAddress3();
        String customerCityState = orderCustomer.getCity()+","+orderCustomer.getState()+" "+orderCustomer.getPostalCode();

        String discountMessage = "";

        if(order.getDiscount()>0){
            discountMessage ="Total includes a ";
            if( order.getDiscountType() == 1 ){
                discountMessage += currencySymbol+order.getDiscount();
            }
            else{
                discountMessage += ""+order.getDiscount()+"%";
            }
            discountMessage +=" discount";
        }

       //String[] msg={""+billNo};
        //String[] msg={};

        String[] msg={distributor.getCompanyName(),distributorAddress,distributorCityState,distributor.getWorkPhone(),
                ""+billNo,customerName,customerAddress,customerCityState,
                orderCustomer.getPhonenumber(),message,message2,currencySymbol+""+formatter.format(totalSubTotal),
                currencySymbol+""+formatter.format(totalTax),currencySymbol+""+formatter.format(order.getTotalAmount()),greetingMessage};

       /*String[] msg={distributor.getFirstName()+" "+distributor.getLastName() , distributorAddress ,distributorCityState ,
               distributor.getWorkPhone(), orderCustomer.getCustomerName() , customerAddress,
               customerCityState , orderCustomer.getPhonenumber(),""+billNo,currencySymbol+""+order.getTotalAmount() ,currencySymbol+" "+order.getOutstandingBalance(), employee.getFirstname()+" "+employee.getLastname() , message,message1,message2,discountMessage };*/
        return msg;
    }
}
