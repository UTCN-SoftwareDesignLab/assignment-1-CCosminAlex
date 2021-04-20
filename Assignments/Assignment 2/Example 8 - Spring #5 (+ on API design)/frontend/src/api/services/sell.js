
import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    changeQuantity(idBook, newQuantity) {
        return HTTP.patch(BASE_URL + "/sell/" + newQuantity, idBook,{headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    search(description) {
        return HTTP.get(BASE_URL + "/sell/" + description,{headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
}