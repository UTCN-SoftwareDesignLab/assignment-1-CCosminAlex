import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    allBooks() {
        return HTTP.get(BASE_URL + "/bookadmin/", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    create( book ) {
        return HTTP.post(BASE_URL + "/bookadmin/", book, {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    edit( book ) {
        return HTTP.put(BASE_URL + `/bookadmin/${book.idBook}`, book, {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    }
};