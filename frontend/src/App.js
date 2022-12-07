import logo from './logo.svg';
import './styles/App.css';

import {Route, Link, useNavigate} from "react-router-dom"
import {BrowserRouter} from "react-router-dom"
import {Routes} from "react-router-dom";
import Login from "./pages/login";
import Admin from "./pages/admin";
import Client from "./pages/client";
import ErrorPage from "./pages/errorPage";
import {AppBar, Box, Button, Toolbar, Typography} from "@mui/material";
import React from "react";
import auth from "./services/auth";

function App() {

  return (
          <BrowserRouter>
              <Routes>
                  <Route exact path="/" element={<Login/>} />
                  <Route  path="/admin" element={<Admin/>} />
                  <Route  path="/client" element={<Client/>} />
                  <Route  path="/*" element={<ErrorPage/>} />
              </Routes>
          </BrowserRouter>
  );
}

export default App;
