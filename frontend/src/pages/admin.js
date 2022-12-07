import React, { useState, useEffect } from "react";
import {useLocation, useNavigate} from "react-router-dom";
import { DataGrid } from '@mui/x-data-grid';
import devices from "../services/devices";
import auth from "../services/auth";
import {
    AppBar,
    Box,
    Button,
    Container, Divider,
    FormControl,
    Grid,
    IconButton,
    InputLabel, MenuItem, Select,
    Toolbar,
    Typography
} from "@mui/material";
import users from "../services/users";
import Dialog from "@mui/material/Dialog";
import DialogTitle from "@mui/material/DialogTitle";
import DialogContent from "@mui/material/DialogContent";
import TextField from "@mui/material/TextField";
import DialogActions from "@mui/material/DialogActions";

function Admin(){

    const user = useLocation().state;
    const [allUsers, setAllUsers]=useState([]);
    const [allDevices, setAllDevices]=useState([]);
    const [refreshKey, setRefreshKey] = useState(0);
    const [userDialogTitle, setUserDialogTitle]=useState("");
    const [openUserDialog, setOpenUserDialog]=useState(false);
    const [deviceDialogTitle, setDeviceDialogTitle]=useState("");
    const [openDeviceDialog, setOpenDeviceDialog]=useState(false);
    const [formUsername, setFormUsername]=useState("");
    const [formPassword, setFormPassword]=useState("");
    const [selectedUser, setSelectedUser]=useState([]);
    const [selectedDevice, setSelectedDevice]=useState([]);
    const [formDescription, setFormDescription]=useState("");
    const [formAddress, setFormAddress]=useState("");
    const [formMaxHourlyConsumption, setFormMaxHourlyConsumption]=useState("");
    const [mappedUser, setMappedUser]=useState("");
    const navigate = useNavigate();

    useEffect(() => {
        if(!user){
            navigate("/");
        }
        async function getAllUsers(){
            const res= await users.findAll();
            setAllUsers(res);
        }
        async function getAllDevices(){
            const res= await devices.findAll();
            setAllDevices(res);
        }
        getAllUsers();
        getAllDevices();
    }, [refreshKey])

    const userColumns = [
        { field: 'id', headerName: 'ID', width: 100 },
        { field: 'username', headerName: 'Username', width: 350 },
        { field: 'role', headerName: 'Role', width: 200 },
    ]

    const deviceColumns = [
        { field: 'id', headerName: 'ID', width: 100 },
        { field: 'description', headerName: 'Description', width: 250 },
        { field: 'address', headerName: 'Address', width: 250 },
        { field: 'maxHourlyConsumption', headerName: 'Maximum Hourly Consumption', width:  250},
        {field: 'username', headerName: 'Assigned User', width: 250}
    ]

    const logOut=()=>{
        auth.logout();
        navigate("/")
    }

    const handleAddUser=()=>{
        setUserDialogTitle("Add User");
        setOpenUserDialog(true);
    }

    const handleUpdateUser=()=>{
        setUserDialogTitle("Update User");
        setFormUsername(selectedUser.username);
        setFormPassword("");
        setOpenUserDialog(true);
    }

    const handleSubmitUser=()=>{
        if(validUserForm()){
            if(userDialogTitle==="Add User"){
                addUser();
            }else{
                updateUser();

            }
            cancel();
            setOpenUserDialog(false);
        }
        else{
            alert("Make sure you insert correct values!")
        }

    }

    const addUser=()=>{
        let signUpRequest={
            username: formUsername,
            password: formPassword,
        };

        auth.register(signUpRequest).then((response)=>{
            alert(response.data.message);
            setRefreshKey(refreshKey + 1)
        })
            .catch(()=>alert("Error creating user"));
    }


    const updateUser=()=>{
        let user={
            id: selectedUser.id,
            username: formUsername,
            password: formPassword
        }
        users.update(user).then(()=>{
            setRefreshKey(refreshKey + 1)
        }) .catch(()=>alert("Error updating user"));
    }

    const deleteUser=()=>{
        const id=selectedUser.id;
        users.delete(id).then(()=> setRefreshKey(refreshKey + 1));
    }

    const cancel=()=>{
        setFormUsername("");
        setFormPassword("");
        setFormDescription("");
        setFormMaxHourlyConsumption("");
        setFormAddress("");
        setMappedUser("");
        setOpenUserDialog(false);
        setOpenDeviceDialog(false);
    }

    const handleAddDevice=()=>{
         setDeviceDialogTitle("Add Device");
         setOpenDeviceDialog(true);
    }

    const handleUpdateDevice=()=>{
        setDeviceDialogTitle("Update Device");
        setFormDescription(selectedDevice.description);
        setFormAddress(selectedDevice.address);
        setFormMaxHourlyConsumption(selectedDevice.maxHourlyConsumption);
        setMappedUser(selectedDevice.username);
        setOpenDeviceDialog(true);
    }

    const handleDeleteDevice=()=>{
        const id=selectedDevice.id;
        devices.delete(id).then(()=> setRefreshKey(refreshKey + 1));
    }

    const validUserForm=()=>{
        if(formUsername==="" || (userDialogTitle==="Add User"&&formPassword==="")){
            return false;
        }
        return true;
    }

    const validDeviceForm=()=>{
        let n = formMaxHourlyConsumption
        if(formDescription==="" || formAddress==="" || formMaxHourlyConsumption==="" || mappedUser==="" ){
            return false;
        }
        return true;
    }

    const handleSubmitDevice=()=>{
        let device={
            id: null,
            description: formDescription,
            address: formAddress,
            maxHourlyConsumption: formMaxHourlyConsumption,
            username: mappedUser,
        };

        if(validDeviceForm()){
            if(deviceDialogTitle==="Add Device"){
                addDevice(device);
            }else{
                updateDevice(device);
            }
            cancel();
            setOpenDeviceDialog(false);
        }
        else{
            alert("Make sure you insert correct values!")
        }
    }

    const addDevice=(device)=>{
        devices.create(device).then((response)=>{
            setRefreshKey(refreshKey + 1)
        })
            .catch(()=>alert("Error creating device"));
    }

    const updateDevice=(device)=>{
        device.id=selectedDevice.id;
        devices.update(device).then(()=>{
            setRefreshKey(refreshKey + 1)
        }) .catch(()=>alert("Error updating device"));
    }

    return(
        <Container disableGutters maxWidth="xl">
            <Box sx={{ flexGrow: 1 }}>
                <AppBar style={{ background: '#01d28e' }} position="static">
                    <Toolbar>
                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                           <h3> Energy Platform</h3>
                        </Typography>
                        <Button color="inherit"  onClick={logOut}>Logout</Button>
                    </Toolbar>
                </AppBar>
            </Box>

            <div className="tables">
                <div className="table-header">
                    <span><h3>Users</h3></span>
                    <span className="row-container">
                            <button type="submit" onClick={handleAddUser}>Add User</button>
                            <button type="submit" onClick={deleteUser}>Delete User</button>
                            <button type="submit" onClick={handleUpdateUser}>Update User</button>
                    </span>
                </div>
                <div>
                    <div className="table">
                        <DataGrid
                            rows={allUsers}
                            columns={userColumns}
                            pageSize={10}
                            rowsPerPageOptions={[10]}
                            onRowClick={(rowInfo) =>
                            { setSelectedUser(rowInfo.row)}}
                        />
                    </div>
                </div>

                <div className="table-header">
                    <span><h3>Devices</h3></span>
                    <span className="row-container">
                            <button type="submit"  onClick={handleAddDevice}>Add Device</button>
                            <button type="submit" onClick={handleUpdateDevice}>Update Device</button>
                            <button type="submit" onClick={handleDeleteDevice}>Delete Device</button>
                        </span>
                </div>

                <div >
                    <div className="table">
                        <DataGrid
                            rows={allDevices}
                            columns={deviceColumns}
                            pageSize={10}
                            rowsPerPageOptions={[10]}
                            onRowClick={(rowInfo) =>
                            { setSelectedDevice(rowInfo.row)}}
                        />
                    </div>
                </div>
            </div>

            <Dialog open={openUserDialog} >
                <DialogTitle>{userDialogTitle}</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="username"
                        label="Username"
                        type="text"
                        fullWidth
                        variant="standard"
                        value={formUsername}
                        onChange={(e) => setFormUsername(e.target.value)}
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="password"
                        label="Password"
                        type="password"
                        fullWidth
                        variant="standard"
                        value={formPassword}
                        onChange={(e) => setFormPassword(e.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={cancel}>Cancel</Button>
                    <Button onClick={handleSubmitUser}>Submit</Button>
                </DialogActions>
            </Dialog>

            <Dialog open={openDeviceDialog} >
                <DialogTitle>{deviceDialogTitle}</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        id="description"
                        label="Description"
                        type="text"
                        fullWidth
                        variant="standard"
                        value={formDescription}
                        onChange={(e) => setFormDescription(e.target.value)}
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="address"
                        label="Address"
                        type="text"
                        fullWidth
                        variant="standard"
                        value={formAddress}
                        onChange={(e) => setFormAddress(e.target.value)}
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        id="maxHourlyConsumption"
                        label="Maximum Hourly Consumption"
                        type="text"
                        variant="standard"
                        fullWidth
                        value={formMaxHourlyConsumption}
                        onChange={(e) => setFormMaxHourlyConsumption(e.target.value)}
                    />
                    <FormControl fullWidth margin="dense" >
                        <InputLabel id="selectUser">mappedUser</InputLabel>
                        <Select
                            labelId="selectUser"
                            value={mappedUser}
                            label="Mapped User"
                            onChange={(e) => setMappedUser(e.target.value)}
                        >
                            {allUsers.map((user)=>
                            {return (<MenuItem key={user.id} value={user.username}>{user.username}</MenuItem>
                            )})}
                        </Select>
                    </FormControl>
                </DialogContent>
                <DialogActions>
                    <Button onClick={cancel}>Cancel</Button>
                    <Button onClick={handleSubmitDevice}>Submit</Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
}
export default Admin;