import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allBooks() {
    return HTTP.get(BASE_URL + "/books", {headers: authHeader()}).then(
        (response) => {
          return response.data;
        }
    );
  },
  create( book ) {
    return HTTP.post(BASE_URL + "/books", book, {headers: authHeader()}).then(
        (response) => {
          return response.data;
        }
    );
  },
    changeQuantity(book, amount) {
        return HTTP.patch(BASE_URL + "/sell/" + book,amount,{headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
  edit( book ) {
    return HTTP.put(BASE_URL + "/books", book, {headers: authHeader()}).then(
        (response) => {
          return response.data;
        }
    );
  },
    deleteBook( book ) {
        return HTTP.delete(BASE_URL + "/books/" + book.id , {
            headers: authHeader(),}).then();

    },
    generateReport(type){
        return HTTP.get(BASE_URL + "/books/export/" + type, { headers: authHeader() })
    },
};