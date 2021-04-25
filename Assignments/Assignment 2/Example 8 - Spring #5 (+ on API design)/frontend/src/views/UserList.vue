<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="booklist">Book List</v-btn>
      <v-btn @click="addUser">Add User</v-btn>
    </v-card-title>
    <v-data-table
        :headers="headers"
        :items="users"
        :search="search"
        @click:row="edit"
    ></v-data-table>
    <EditUserDialog
        :opened="dialogVisible"
        :user="selectedUser"
    ></EditUserDialog>
  </v-card>
</template>

<script>
import api from "../api";
import router from "@/router";
import EditUserDialog from "@/components/EditUserDialog";
export default {
  name: "UserList",
  components: { EditUserDialog },
  data() {
    return {
      users: [],
      search: "",
      headers: [
        {
          text: "Username",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Email", value: "email" },
        { text: "Roles", value: "roles" },
        { value:"password"},
      ],
      dialogVisible: false,
      selectedUser: {},
    };
  },
  methods: {
    addUser(){
      this.dialogVisible = true;
    },
    booklist(){
      router.push("./bookadmin");
    },
    edit(user){
      this.selectedUser=user;
      this.dialogVisible = true;
    }


  },

  async created() {
    this.users = await api.users.allUsers();
  }
};
</script>

<style scoped></style>
