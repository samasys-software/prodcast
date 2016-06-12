package com.primeforce.prodcast;

import com.primeforce.prodcast.businessobjects.*;
import com.primeforce.prodcast.businessobjects.Collection;
import com.primeforce.prodcast.dao.DatabaseManager;
import com.primeforce.prodcast.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by sarathan732 on 4/22/2016.
 */
@Named
@Path("/global/")
public class GlobalRest {


    private final DatabaseManager databaseManager;

    @Autowired
    public GlobalRest(DatabaseManager manager) {
        databaseManager = manager;
    }

    @GET
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public LoginDTO authenticate(@QueryParam("userid") String id, @QueryParam("password") String password) {

        LoginDTO dto = new LoginDTO();
        Employee employee = null;

        try {
            employee = databaseManager.login(id, password);
            if (employee == null) dto.setSuccess(false);
            else dto.setSuccess(true);
            dto.setEmployee(employee);
        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }
        return dto;
    }

    @POST
    @Path("loginp")
    @Produces(MediaType.APPLICATION_JSON)
    public LoginDTO authenticatePost(@RequestBody UserDTO user) {

        LoginDTO dto = new LoginDTO();
        Employee employee = null;

        try {
            employee = databaseManager.login(user.getUserId(), user.getPassword());
            if (employee == null) dto.setSuccess(false);
            else dto.setSuccess(true);
            dto.setEmployee(employee);
        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }
        return dto;
    }


    @GET
    @Path("areas")
    @Produces(MediaType.APPLICATION_JSON)
    public AreaDTO getAreas(@QueryParam("employeeId") String employeeId) {

        AreaDTO dto = new AreaDTO();
        try {
            List<Area> areas = databaseManager.fetchAreasForEmployee(Long.parseLong(employeeId));
            dto.setAreas(areas);
        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }
        return dto;

    }


    @GET
    @Path("customers")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerListDTO getCustomerList(@QueryParam("employeeId") String employeeId) {
        CustomerListDTO dto = new CustomerListDTO();

        try {
            List customers = databaseManager.fetchCustomers(Long.parseLong(employeeId));
            dto.setCustomerList(customers);
        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }
        return dto;
    }

    @GET
    @Path("customer")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerDTO getCustomer(@QueryParam("id") String id) {
        CustomerDTO dto = new CustomerDTO();

        try {
            Customer customer = databaseManager.getCustomer(id);
            dto.setCustomer(customer);

            customer.setOutstandingBill(databaseManager.fetchOutstandingBills(id));
        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }
        return dto;
    }

    @GET
    @Path("products")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductListDTO getProductList(@QueryParam("employeeId") String employeeId) {
        ProductListDTO dto = new ProductListDTO();
        try {
            List<Product> list = databaseManager.fetchProductsForDistributor(Long.parseLong(employeeId));
            dto.setProductList(list);
        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }
        return dto;
    }

    @GET
    @Path("billdetails")
    @Produces(MediaType.APPLICATION_JSON)
    public OrderDTO getBillDetails(@QueryParam("billId") String id) {
        OrderDTO dto = new OrderDTO();

        try {
            Order order = databaseManager.fetchOrder(Long.parseLong(id));
            dto.setOrder(order);
        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }
        return dto;
    }


    @POST
    @Path("collection")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerDTO addCollectionPayment(@FormParam("employeeId") String employeeId, @FormParam("billId") String billId, @FormParam("amount") String amount, @FormParam("customerId") String customerId) {
        CustomerDTO dto = new CustomerDTO();
        try {
            int rowCount = databaseManager.updateCollectionPayment(Long.parseLong(employeeId), Long.parseLong(billId), Float.parseFloat(amount));
            if (rowCount == 0) {
                dto.setError(true);
                dto.setErrorMessage("Unable to update collection. Please try again!");
            } else {
                Customer customer = databaseManager.getCustomer(customerId);
                customer.setOutstandingBill(databaseManager.fetchOutstandingBills(customerId));
                dto.setCustomer(customer);
            }
        } catch (Exception er) {
            dto.setError(true);
            dto.setErrorMessage(er.toString());
            er.printStackTrace();
        }

        return dto;
    }

    @POST
    @Path("saveCustomer")
    @Produces(MediaType.APPLICATION_JSON)
    public ProdcastDTO saveCustomer(@FormParam("employeeId") String employeeId,
                                    @FormParam("customerName") String customerName,
                                    @FormParam("customerType") String customerType,
                                    @FormParam("areaId") String areaId,
                                    @FormParam("weekDay") String weekDay,
                                    @FormParam("firstName") String firstName,
                                    @FormParam("lastName") String lastName,
                                    @FormParam("emailAddress") String emailAddress,
                                    @FormParam("cellPhone") String cellPhoneNumber,
                                    @FormParam("phoneNumber") String phoneNumber,
                                    @FormParam("unitNumber") String unitNumber,
                                    @FormParam("billingAddress1") String billingAddress1,
                                    @FormParam("billingAddress2") String billingAddress2,
                                    @FormParam("billingAddress3") String billingAddress3,
                                    @FormParam("city") String city,
                                    @FormParam("state") String state,
                                    @FormParam("country") String countryId,
                                    @FormParam("postalCode") String postalCode,
                                    @FormParam("notes") String notes) {
        ProdcastDTO dto = new ProdcastDTO();
        try {
            int rowCount = databaseManager.createCustomer(
                    Long.parseLong(employeeId), customerName, customerType, Long.parseLong(areaId), weekDay, firstName, lastName,
                    emailAddress, cellPhoneNumber, phoneNumber, unitNumber,
                    billingAddress1, billingAddress2, billingAddress3, city, state, countryId, postalCode, notes);

            if (rowCount == 0) {
                dto.setError(true);
                dto.setErrorMessage("Unable to update collection. Please try again!");
            }

        } catch (Exception er) {
            dto.setError(true);
            dto.setErrorMessage(er.toString());
            er.printStackTrace();
        }

        return dto;
    }

    @POST
    @Path("saveOrder")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerDTO saveOrder(@RequestBody OrderDetailDTO orderDto) {

        System.out.println(orderDto);
        CustomerDTO dto = new CustomerDTO();

        try {
            //Map OrderDetailDTO to Business Object
            Order order = new Order();
            order.setCustomerId(Long.parseLong(orderDto.getCustomerId()));
            order.setEmployeeId(Long.parseLong(orderDto.getEmployeeId()));
            List<OrderEntry> orderEntries = new LinkedList<OrderEntry>();
            order.setOrderEntries(orderEntries);
            for (OrderEntryDTO entryDto : orderDto.getEntries()) {
                OrderEntry entry = new OrderEntry();
                entry.setQuantity(Integer.parseInt(entryDto.getQuantity()));
                entry.setProductId(Long.parseLong(entryDto.getProductId()));
                orderEntries.add(entry);
            }

            float paymentAmount = Float.parseFloat(orderDto.getPaymentAmount());
            databaseManager.saveOrder(order, paymentAmount);

            Customer customer = databaseManager.getCustomer(orderDto.getCustomerId());
            customer.setOutstandingBill(databaseManager.fetchOutstandingBills(orderDto.getCustomerId()));
            dto.setCustomer(customer);

            dto.setError(false);
        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }
        return dto;
    }

    @GET
    @Path("salesReport")
    @Produces(MediaType.APPLICATION_JSON)
    public ReportDTO getReport(@QueryParam("reportType") String reportType, @QueryParam("employeeId") String employeeId) {
        ReportDTO dto = new ReportDTO();
        final long DAY = 24 * 60 * 60 * 1000;
        try {

            Calendar cal = Calendar.getInstance();
            java.sql.Date startDate = java.sql.Date.valueOf("2016-04-20");
            java.sql.Date endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

            if (reportType.equals("today")) {
                startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                endDate = startDate;
            } else if (reportType.equals("yesterday")) {
                startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime() - DAY);
                endDate = startDate;
            } else if (reportType.equals("week")) {
                endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            } else if (reportType.equals("last7")) {
                endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime() - 7 * DAY);
            } else if (reportType.equals("month")) {
                endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                cal.set(Calendar.DAY_OF_MONTH, 1);
            } else if (reportType.equals("last30")) {
                endDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                startDate = new java.sql.Date(Calendar.getInstance().getTime().getTime() - 30 * DAY);
            }

            if (startDate == endDate || startDate.equals(endDate)) {
                dto.setReportDates(startDate.toString());
            } else {
                dto.setReportDates(startDate.toString() + "-" + endDate.toString());
            }
            List<Order> orders = databaseManager.fetchSalesReport(startDate, endDate, Long.parseLong(employeeId));
            List<Collection> collections = databaseManager.fetchCollectionReport(startDate, endDate, Long.parseLong(employeeId));
            float totalSales = 0;
            float totalBalance = 0;
            for (Order order : orders
                    ) {
                totalSales += order.getTotalAmount();
                totalBalance += order.getOutstandingBalance();
            }
            dto.setBalance(totalBalance);
            Map<String, Float> collectionGroup = new HashMap<String, Float>();
            float totalCollection = 0;
            for (Collection collection : collections
                    ) {
                totalCollection += collection.getAmountPaid();
                String paymentType = collection.getPaymentType();
                if (paymentType == null || paymentType.trim().length() == 0) paymentType = "CASH";
                if (collectionGroup.containsKey(paymentType)) {
                    float val = collectionGroup.get(paymentType);
                    val += collection.getAmountPaid();
                    collectionGroup.put(paymentType, val);
                } else
                    collectionGroup.put(paymentType, collection.getAmountPaid());
            }

            dto.setTotalSales(totalSales);
            dto.setTotalCollection(totalCollection);
            dto.setCollectionGroup(collectionGroup);
            dto.setOrders(orders);
            dto.setCollections(collections);

        } catch (Exception er) {
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString());
        }
        return dto;
    }

    @POST
    @Path("saveExpense")
    @Produces(MediaType.APPLICATION_JSON)
    public ProdcastDTO saveExpense(@FormParam("account") String account, @FormParam("catgId") int categoryId,
                                   @FormParam("desc1") String desc1, @FormParam("desc2") String desc2,
                                   @FormParam("amount") double amount, @FormParam("payMode") String paymode,
                                   @FormParam("ipAddress") String ipAddress, @FormParam("userId") String userId) {
        ProdcastDTO dto = new ProdcastDTO();
        try {
            int rowCount = databaseManager.saveExpense(0, account, categoryId, desc1, desc2, amount, paymode, ipAddress, userId);
            if (rowCount == 0) {
                dto.setError(true);
                dto.setErrorMessage("Unable to save expense. Please try again!");
            }
        } catch (Exception er) {
            dto.setError(true);
            dto.setErrorMessage(er.toString());
            er.printStackTrace();
        }
        return dto;
    }

    @POST
    @Path("saveExpenseCategory")
    @Produces(MediaType.APPLICATION_JSON)
    public ProdcastDTO saveExpenseCategory(@FormParam("catgDesc") String catgDesc, @FormParam("ipAddress") String ipAddress,
                                           @FormParam("userId") String userId) {
        ProdcastDTO dto = new ProdcastDTO();
        try {
            int rowCount = databaseManager.saveExpenseCategory(catgDesc, ipAddress, userId);
            if (rowCount == 0) {
                dto.setError(true);
                dto.setErrorMessage("Unable to save expense. Please try again!");
            }
        } catch (Exception er) {
            dto.setError(true);
            dto.setErrorMessage(er.toString());
            er.printStackTrace();
        }
        return dto;
    }

}