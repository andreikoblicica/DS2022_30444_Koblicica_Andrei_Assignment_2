import axios from "axios";

export const BASE_URL = "http://localhost:8089/api";
export const HTTP = axios.create({
    baseURL: BASE_URL,
});
//axios.defaults.headers.common["Authorization"] = "Bearer "+JSON.parse(localStorage.getItem("user")).token


export default function authHeader() {
    let user = JSON.parse(localStorage.getItem("user"));
    if (user && user.token) {

        return { Authorization: "Bearer " + user.token };
    } else {
        return {};
    }
}