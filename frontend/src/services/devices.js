import authHeader, { BASE_URL, HTTP } from "./http";

export default {
    findAll() {
        return HTTP.get(BASE_URL + "/admin/devices", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    findByUserId(id) {
        return HTTP.get(BASE_URL + "/devices/"+id, { headers: authHeader() }).then(
            (response) => {

                return response.data;
            }
        );
    },
    findConsumption(id, date) {
        return HTTP.get(BASE_URL + "/devices/"+id+"/"+date, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    // findById(id) {
    //     return HTTP.get(BASE_URL + "/admin/"+id, { headers: authHeader() }).then(
    //         (response) => {
    //             return response.data;
    //         }
    //     );
    // },
    // findByUsername(username) {
    //     return HTTP.get(BASE_URL + "/admin/username/"+username, { headers: authHeader() }).then(
    //         (response) => {
    //             return response.data;
    //         }
    //     );
    // },
    create(device) {
        return HTTP.post(BASE_URL + "/admin/devices", device, { headers: authHeader() }).then(
            (response) => {
                return response;
            }
        );
    },
    update(device) {
        return HTTP.put(BASE_URL + "/admin/devices", device, {
            headers: authHeader(),
        }).then((response) => {
            return response;
        });
    },
    delete(id) {
        return HTTP.delete(BASE_URL + "/admin/devices/"+id, {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },

};