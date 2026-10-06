package dev.eyup.qe.model.request;

import java.util.List;
import java.util.Map;

public record PetApiCreatePetModel (
        int id,
         String name,
         Map<String, Object> category,
         String[] photoUrls,
         List<Map<String, Object>> tags,
         String status
){
    /*
                    {
                  "id": 10,
                  "name": "doggie",
                  "category": {
                    "id": 1,
                    "name": "Dogs"
                  },
                  "photoUrls": [
                    "string"
                  ],
                  "tags": [
                    {
                      "id": 0,
                      "name": "string"
                    }
                  ],
                  "status": "available"
                }
     */


}
