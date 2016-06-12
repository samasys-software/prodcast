package com.primeforce.prodcast;

import com.primeforce.prodcast.businessobjects.*;
import com.primeforce.prodcast.businessobjects.Collection;
import com.primeforce.prodcast.dao.CompanySetting;
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
@Path("/distributor/")
public class DistributorRest {


    private final DatabaseManager databaseManager;

    @Autowired
    public DistributorRest(DatabaseManager manager) {
        databaseManager = manager;
    }

    @GET
    @Path("getEmployees")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<List<Employee>> getEmployees(@QueryParam("employeeId") String employeeId) {
        AdminDTO<List<Employee>> dto = new AdminDTO<List<Employee>>();

        try {
            List<Employee> employees = databaseManager.fetchEmployeesForDistributor(Long.parseLong(employeeId ));
            dto.setResult( employees );
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }

    @GET
    @Path("getBrands")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<List<Brand>> getBrands(@QueryParam("employeeId") String employeeId) {
        AdminDTO<List<Brand>> dto = new AdminDTO<List<Brand>>();

        try {
            List<Brand> brands = databaseManager.fetchBrandsForDistributor(Long.parseLong(employeeId ));
            dto.setResult( brands);
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }


    @POST
    @Path("saveBrand")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<List<Brand>> saveBrand(@FormParam("employeeId") String employeeId, @FormParam("brandName") String brandName){
        AdminDTO<List<Brand>> dto = new AdminDTO<List<Brand>>();
        try {
            List<Brand> brands = databaseManager.saveBrandForDistributor(Long.parseLong(employeeId), brandName);
            if( brands == null ) {
                dto.setError(true);
                dto.setErrorMessage("Unable to save brand");
            }
            else
            dto.setResult( brands );
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }

        return dto;
    }

    @GET
    @Path("setting")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<CompanySetting> getSetting(@QueryParam("employeeId") String employeeId) {

        AdminDTO dto = new AdminDTO();
        try {
            dto.setResult( databaseManager.fetchCompanySetting(Long.parseLong( employeeId) ));
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError(true);
            dto.setErrorMessage(er.toString() );
        }
        return dto;

    }
    @GET
    @Path("getAreas")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<List<Area>> getAreas(@QueryParam("employeeId") String employeeId) {
        AdminDTO<List<Area>> dto = new AdminDTO<List<Area>>();

        try {
            List<Area> areas = databaseManager.fetchAreasForDistributor(Long.parseLong(employeeId ));
            dto.setResult( areas );
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }


    @POST
    @Path("saveArea")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<List<Area>> saveArea(@FormParam("employeeId") String employeeId, @FormParam("areaName") String areaName){
        AdminDTO<List<Area>> dto = new AdminDTO<List<Area>>();
        try {
            List<Area> areas = databaseManager.saveAreaForDistributor(Long.parseLong(employeeId), areaName );
            if( areas == null ) {
                dto.setError(true);
                dto.setErrorMessage("Unable to save brand");
            }
            else
                dto.setResult( areas );
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }

        return dto;
    }

    @GET
    @Path("getCategory")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<List<Category>> getCategories(@QueryParam("employeeId") String employeeId) {
        AdminDTO<List<Category>> dto = new AdminDTO<List<Category>>();

        try {
            List<Category> categories = databaseManager.fetchCategoriesForDistributor(Long.parseLong(employeeId ));
            dto.setResult( categories );
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }


    @POST
    @Path("saveCategory")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<List<Category>> saveCategory(@FormParam("employeeId") String employeeId, @FormParam("categoryName") String categoryName){
        AdminDTO<List<Category>> dto = new AdminDTO<List<Category>>();
        try {
            List<Category> areas = databaseManager.saveCategoryForDistributor(Long.parseLong(employeeId), categoryName );
            if( areas == null ) {
                dto.setError(true);
                dto.setErrorMessage("Unable to save brand");
            }
            else
                dto.setResult( areas );
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }

        return dto;
    }

    @GET
    @Path("getSubCategory")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<List<SubCategory>> getSubCategories(@QueryParam("employeeId") String employeeId) {
        AdminDTO<List<SubCategory>> dto = new AdminDTO<List<SubCategory>>();

        try {
            List<SubCategory> categories = databaseManager.fetchSubCategoriesForDistributor(Long.parseLong(employeeId ));
            dto.setResult( categories );
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }


    @POST
    @Path("saveSubCategory")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<List<SubCategory>> saveSubcategories(@FormParam("employeeId") String employeeId, @FormParam("subCategoryName") String subCategoryName, @FormParam("categoryId") String categoryId){
        AdminDTO<List<SubCategory>> dto = new AdminDTO<List<SubCategory>>();
        try {
            List<SubCategory> areas = databaseManager.saveSubCategoryForDistributor(Long.parseLong(employeeId), subCategoryName , Long.parseLong( categoryId ) );
            if( areas == null ) {
                dto.setError(true);
                dto.setErrorMessage("Unable to save brand");
            }
            else
                dto.setResult( areas );
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }

        return dto;
    }

    @GET
    @Path("getProducts")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<List<Product>> getProducts(@QueryParam("employeeId") String employeeId) {
        AdminDTO<List<Product>> dto = new AdminDTO<List<Product>>();

        try {
            List<Product> products = databaseManager.fetchProductsForDistributor(Long.parseLong(employeeId ));
            dto.setResult( products );
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }
        return dto;
    }


    @POST
    @Path("saveProduct")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<List<Product>> saveProduct(@FormParam("employeeId") String employeeId, @FormParam("productId") String productId, @FormParam("productName") String productName, @FormParam("productDesc") String productDesc, @FormParam("productSku") String productSku, @FormParam("unitPrice") String unitPrice,@FormParam("priceType") String priceType,@FormParam("categoryId") String categoryId, @FormParam("subCategoryId") String subCategoryId, @FormParam("brandId") String brandId, @FormParam("active") String active  ){
        AdminDTO<List<Product>> dto = new AdminDTO<List<Product>>();
        try {
            List<Product> result = databaseManager.saveProductForDistributor(Long.parseLong(employeeId), Long.parseLong( productId ) , productName , productDesc , productSku , Float.parseFloat(unitPrice) , priceType,Long.parseLong(categoryId ) , Long.parseLong(subCategoryId ) , Long.parseLong(brandId ), Boolean.parseBoolean( active ) );
            if( result == null ) {
                dto.setError(true);
                dto.setErrorMessage("Unable to save brand");
            }
            else
                dto.setResult( result );
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }

        return dto;
    }

    @POST
    @Path("saveEmployee")
    @Produces(MediaType.APPLICATION_JSON)
    public AdminDTO<List<Employee>> saveEmployee(@FormParam("employeeId") String employeeId,
                                                 @FormParam("newEmployeeId") String newEmployeeId,
                                                 @FormParam("firstName") String firstName,
                                                 @FormParam("lastName") String lastName,
                                                 @FormParam("title") String title,
                                                 @FormParam("sex") String sex,
                                                 @FormParam("dateOfBirth") String dateOfBirth,
                                                 @FormParam("salary") String salary,
                                                 @FormParam("hireDate") String hireDate,
                                                 @FormParam("terminationDate") String terminationDate,
                                                 @FormParam("allowance") String allowance,
                                                 @FormParam("userRole") String userRole,
                                                 @FormParam("address1") String address1,
                                                 @FormParam("address2") String address2,
                                                 @FormParam("address3") String address3,
                                                 @FormParam("city") String city,
                                                 @FormParam("state") String state,
                                                 @FormParam("countryId") String countryId,
                                                 @FormParam("postalCode") String postalCode,
                                                 @FormParam("location") String location,
                                                 @FormParam("cellPhone") String cellPhone,
                                                 @FormParam("homePhone") String homePhone,
                                                 @FormParam("workPhone") String workPhone,
                                                 @FormParam("emailId") String emailId,
                                                 @FormParam("active") String active,
                                                 @FormParam("comments") String comments,
                                                 @FormParam("areaIds") String areas
                                                 ){
        AdminDTO<List<Employee>> dto = new AdminDTO<List<Employee>>();
        try {
            if( newEmployeeId == null || newEmployeeId.trim().equals("")) newEmployeeId = "0";
            if( terminationDate== null || terminationDate.trim().equals("null")|| terminationDate.trim().length()== 0 ) terminationDate = null;
            List<Employee> result = databaseManager.saveEmployeeForDistributor(Long.parseLong(employeeId),
                    firstName,lastName,title, sex,dateOfBirth,salary, hireDate, terminationDate, allowance,
                    userRole, address1 , address2, address3 , city, state, countryId ,postalCode,
                    location,cellPhone , homePhone, workPhone , emailId , active, comments, areas , Long.parseLong( newEmployeeId));
            if( result == null ) {
                dto.setError(true);
                dto.setErrorMessage("Unable to save Employee");
            }
            else
                dto.setResult( result );
        }
        catch(Exception er){
            er.printStackTrace();
            dto.setError( true );
            dto.setErrorMessage( er.toString() );
        }

        return dto;
    }



}