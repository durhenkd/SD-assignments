import axios from "axios";
import { AccountState } from "./state/reducers/accountReduces";

export const spring_axios = axios.create({
  baseURL: "http://localhost:8080/test/api",
  withCredentials: true,
});

export const login = async (username: string, password: string): Promise<AccountState> => {

  const params = new URLSearchParams();
  params.append("username", username);
  params.append("password", password);
  
  return await spring_axios
    .post("/login", {}, { params: params })
    .then((res) => {
      if (res.data.role === "USER"){
        return { name: res.data.username, id: res.data.id, isAdmin: false };
      } else {
        return { name: res.data.username, id: res.data.id, isAdmin: true };
      }
    })
    .catch((error) => {
      if (error.response) {
        // The request was made and the server responded with a status code
        // that falls out of the range of 2xx
        console.log(error.response.data);
        console.log(error.response.status);
        console.log(error.response.headers);
      } else if (error.request) {
        // The request was made but no response was received
        // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
        // http.ClientRequest in node.js
        console.log(error.request);
        console.log(error.message);
      } else {
        // Something happened in setting up the request that triggered an Error
        console.log("Error", error.message);
      }
      return { name: "", id: -1, isAdmin: false };
    });
};
