package com.primeforce.prodcast.dto;


import com.primeforce.prodcast.businessobjects.StoreType;

import java.util.List;

/**
 * Created by user on 12/29/2016.
 */


    public class StoreTypeDTO extends ProdcastDTO {
        private List<StoreType> result;


        public List<StoreType> getResult()
        {
            return result;
        }

        public void setResult(List<StoreType> result)
        {
            this.result = result;
        }





    }
