package com.primeforce.prodcast;

import com.primeforce.prodcast.businessobjects.Registration;
import com.primeforce.prodcast.businessobjects.StoreType;
import com.primeforce.prodcast.dao.DatabaseManager;
import com.primeforce.prodcast.businessobjects.Distributor;
import com.primeforce.prodcast.dto.*;
import com.primeforce.prodcast.util.Amazon;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by sarathan732 on 5/23/2016.
 */
@Named
@Path("/superadmin/")
public class SuperAdminRest {

    private final DatabaseManager databaseManager;

    @Autowired
    public SuperAdminRest(DatabaseManager manager) {
        databaseManager = manager;
    }

    @GET
    @Path("distributors")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<List<Distributor>> getDistributors(@QueryParam("employeeId") long employeeId){

        AdminDTO<List<Distributor>> dto = new AdminDTO<List<Distributor>>();
        try{
            dto.setResult( databaseManager.getDistributors());
        }
        catch(Exception er ){
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
            er.printStackTrace();
        }
         return dto;
    }



    @POST
    @Path("saveDistributor")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<List<Distributor>> saveDistributor(  @FormParam("employeeId")   long employeeId,
                                                         @FormParam("companyName") String companyName,
                                                         @FormParam("type") String type,
                                                         @FormParam("firstName") String firstName,
                                                         @FormParam("lastName") String lastName,
                                                         @FormParam("title") String title,
                                                         @FormParam("emailAddress") String emailAddress,
                                                         @FormParam("cellPhone") String cellPhone,
                                                         @FormParam("homePhone") String homePhone,
                                                         @FormParam("workPhone") String workPhone,
                                                         @FormParam("address1") String address1,
                                                         @FormParam("address2") String address2,
                                                         @FormParam("address3") String address3,
                                                         @FormParam("city") String city,
                                                         @FormParam("state") String state,
                                                         @FormParam("postalCode") String postalCode,
                                                         @FormParam("country") String country,
                                                         @FormParam("timezone") String timezone,
                                                         @FormParam("gender") String gender,
                                                         @FormParam("manufacturer") boolean manufacturer,
                                                         @FormParam("active") String active,
                                                         @FormParam("comments") String comments,
                                                         @FormParam("openToPublic") String openToPublic,
                                                         @FormParam("fulfillmentType") String fulfillmentType,
                                                         @FormParam("newDistributorId") String newDistributorId
                                                      ){
        AdminDTO<List<Distributor>> dto = new AdminDTO<List<Distributor>>();
        try {
        if(newDistributorId== null || newDistributorId.trim().length()==0 || newDistributorId.trim().equals("null")) newDistributorId="0";

        System.out.println("SAVEDISTRIBUTOR "+newDistributorId);
        Long employeeForEmail = databaseManager.fetchEmailInUse(emailAddress);
            System.out.println("EMPLOYEEFOREMAIL="+employeeForEmail);
       long employeeIdForDistributor=databaseManager.getEmployeeIdForDistributor(Long.parseLong(newDistributorId));
            System.out.println("EMPLOYEEIDFORDISTRIBUTOR="+employeeIdForDistributor);
        if( employeeForEmail != null )
        {

            if(!employeeForEmail.equals( employeeIdForDistributor)) {
                dto.setErrorMessage("The email address is already in use. Please use a different email address");
                dto.setError(true);
                return dto;
            }
        }


            List<Distributor> result;
            Boolean sendMail=false;
            String oldMailId=null;
            if( newDistributorId.equals("0"))
            {

                result  = databaseManager.saveDistributor(employeeId, companyName, type, firstName, lastName, title,
                        emailAddress, cellPhone, homePhone, workPhone, address1, address2, address3,
                        city, state, postalCode, country, timezone, gender, manufacturer, active, comments,openToPublic,fulfillmentType);
               // String emailId[] = {emailAddress};


               Amazon.sendEmail(emailAddress,"WELCOME","WELCOME TO PRODCAST ");
                //Notifier.sendNotification("WELCOME",emailIds );
            }
            else
                {
                oldMailId=databaseManager.getMailIdForDistributor(Long.parseLong(newDistributorId));


                result= databaseManager.updateDistributor(companyName, type, firstName, lastName, title,
                        emailAddress, cellPhone, homePhone, workPhone, address1, address2, address3,
                        city, state, postalCode, country, timezone, active, gender, manufacturer, comments, employeeId, Long.parseLong(newDistributorId),openToPublic,fulfillmentType);


                    if(!emailAddress.equals(oldMailId))
                    {
                        sendMail=true;
                    }
                }




            if( result == null )
            {
                dto.setError(true);
                dto.setErrorMessage("Unable to save Distributor");
            }
            else {

                dto.setResult(result);
                if (sendMail) {
                    Amazon.sendEmail(emailAddress, "Your Details Has Been Updated Successfully", "Your Details Has Been Updated Successfully And Your Mail Id Has Been Changed Successfully in PRODCAST Details..\nYour Mail Id is " + emailAddress + " and your Password is " + databaseManager.getPasswordForDistributor(emailAddress));
                }
                else
                {
                    Amazon.sendEmail(emailAddress,"Your Details Has Been Update Successfully","Your Details Has Been Updated Successfully in PRODCAST Details");
                }

                // Distributor distributor = databaseManager.getDistributor(Long.parseLong(newDistributorId));


            }

        }
        catch(Exception er){
            er.printStackTrace();
            dto.setErrorMessage( er.toString());
            dto.setError(true);
        }

        return dto;
    }


    @GET
    @Path("getRegistrationDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public RegistrationDTO getRegistrationDetails(){

        RegistrationDTO dto = new RegistrationDTO();
        List<Registration> registration=null;
        try{
            registration=databaseManager.getAllRegistors();
            dto.setResult( registration);
        }
        catch(Exception er ){
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
            er.printStackTrace();
        }
        return dto;
    }

    @POST
    @Path("registration")
    @Produces(MediaType.APPLICATION_JSON)

    public RegistrationDTO registration(  @FormParam("registrationId") String registrationId,
                                    @FormParam("status") String status,
                                    @FormParam("comments") String comments) {
        RegistrationDTO dto = new RegistrationDTO();

        try {
            int rowCount;
            rowCount=databaseManager.updateRegistration(Long.parseLong(registrationId),status,comments);
            if( rowCount == 0 ) {
                dto.setError(true);
                dto.setErrorMessage("Unable to update collection. Please try again!");
            }
            else{
                dto.setResult( databaseManager.getAllRegistors());
            }

        } catch (Exception er) {
            dto.setError(true);
            dto.setErrorMessage(er.toString());
            er.printStackTrace();

        }


        return dto;
    }
    /*

    @POST
    @Path("saveStoreType")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<List<StoreType>> saveStoreType( @FormParam("storeTypeId") String storeTypeId,
                                                    @FormParam("storeTypeName") String storeTypeName)

                                                   // @FormParam("active") boolean active)
    {
        AdminDTO<List<StoreType>> dto = new AdminDTO<List<StoreType>>();
        try {
            List<StoreType> storeType;

            if (storeTypeId == null || storeTypeId.trim().length() == 0)
            {

                storeType = databaseManager.saveStoreType(storeTypeName);
                if (storeType == null) {
                    dto.setError(true);
                    dto.setErrorMessage("Unable to save storeType");
                }
                else
                    dto.setResult(storeType);

            }
            else {
                int rowCount = databaseManager.updateStore(storeTypeName, Long.parseLong(storeTypeId));
                if (rowCount != 1) {
                    dto.setError(true);
                    dto.setErrorMessage("Unable to save storeType");

                }
            }


        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }

        return dto;
    }




*/





    @POST
    @Path("saveStoreType")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO saveStoreType(@FormParam("storeTypeName") String storeTypeName,
                                  @FormParam("active") boolean active)
    {
        AdminDTO dto = new AdminDTO ();
        try {
               List<StoreType> newStoreType = databaseManager.createStoreType(storeTypeName);
                dto.setResult(newStoreType);

        }
        catch(Exception er){
            dto.setError( true );
            dto.setErrorMessage( er.toString());
            er.printStackTrace();
        }

        return  dto;
    }
    @POST
    @Path("updateStoreType")
    @Produces(MediaType.APPLICATION_JSON)

    public StoreTypeDTO updateStoreType(@FormParam("storeTypeId") String storeTypeId,
                                        @FormParam("storeTypeName") String storeTypeName)


    {
        StoreTypeDTO dto = new StoreTypeDTO();

        try {
            int rowCount;
            rowCount= databaseManager.updateStoreType(storeTypeName,Long.parseLong(storeTypeId));

            if( rowCount == 0 ) {
                dto.setError(true);
                dto.setErrorMessage("Unable to update StoreType. Please try again!");
            }
            else{
                dto.setResult( databaseManager.fetchStoreType());
            }

        } catch (Exception er) {
            dto.setError(true);
            dto.setErrorMessage(er.toString());
            er.printStackTrace();

        }


        return dto;
    }
/*

    @POST
    @Path("deleteStoreType")
    @Produces(MediaType.APPLICATION_JSON)

    public AdminDTO deleteStoreType(@FormParam("storeTypeId") String storeTypeId)

    {
        AdminDTO dto = new AdminDTO();


        try {


            int rowCount = databaseManager.deleteStoreType( Long.parseLong(storeTypeId) );



            if (rowCount!=1) {
                dto.setError(true);
                dto.setErrorMessage("Unable to delete storetype");
            }
            else
            {
                dto.setResult(databaseManager.fetchStoreType());

            }




        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }

        return dto;
    }



*/


}
