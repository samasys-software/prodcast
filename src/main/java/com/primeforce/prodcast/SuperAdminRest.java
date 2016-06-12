package com.primeforce.prodcast;

import com.primeforce.prodcast.dao.DatabaseManager;
import com.primeforce.prodcast.dao.Distributor;
import com.primeforce.prodcast.dto.AdminDTO;
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
                                                         @FormParam("gender") String gender,
                                                         @FormParam("manufacturer") boolean manufacturer,
                                                         @FormParam("active") boolean active,
                                                         @FormParam("comments") String comments,
                                                         @FormParam("newDistributorId") String newDistributorId
                                                      ){
        if(newDistributorId== null || newDistributorId.trim().length()==0 || newDistributorId.trim().equals("null")) newDistributorId="0";
        AdminDTO<List<Distributor>> dto = new AdminDTO<List<Distributor>>();
        try {
            dto.setResult( databaseManager.saveDistributor(employeeId,
                    companyName,
                    type,
                    firstName,
                    lastName,
                    title,
                    emailAddress,
                    cellPhone,
                    homePhone,
                    workPhone,
                    address1,
                    address2,
                    address3,
                    city,
                    state,
                    postalCode,
                    country,
                    gender,
                    manufacturer,
                    active,
                    comments
            ));

        }
        catch(Exception er){
            er.printStackTrace();
            dto.setErrorMessage( er.toString());
            dto.setError(true);
        }

        return dto;
    }


}
