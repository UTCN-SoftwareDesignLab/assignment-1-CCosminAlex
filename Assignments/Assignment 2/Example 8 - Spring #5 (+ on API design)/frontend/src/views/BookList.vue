<template>
  <v-card>
    <v-card-title>
      SellBook
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="edit">Sell Book</v-btn>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="books"
        :search="search"
        @click:row="edit"
    ></v-data-table>
    <SellDialog
      :opened="dialogVisible"
      :book="selectedBook"
      @refresh="refreshList"
    ></SellDialog>
  </v-card>
</template>

<script>
import api from "../api";
import SellDialog from "@/components/SellDialog";


export default {
  name: "BookList",
  components: { SellDialog },
  data() {
    return {
      books: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Quantity", value: "quantity" },
        { text: "price", value: "price" },
      ],
      dialogVisible: false,
      selectedBook: {},
    };
  },
  methods: {
    edit(idBook) {
      this.selectedBook=idBook;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedBook = {};
      this.books = await api.books.allBooks();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
