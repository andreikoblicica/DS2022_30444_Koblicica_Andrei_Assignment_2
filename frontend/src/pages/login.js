import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import auth from "../services/auth";

function Login(){

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    //const [loggedUser, setLoggedUser] = useState();


    const navigate = useNavigate();

    function attemptLogIn(e){
        e.preventDefault();
        let loginRequest={
            username: username,
            password: password
        }
        auth.login(loginRequest).then(
            (user) => {
                if(user.role==="Admin"){
                    navigate("/admin",{state: user});
                }
                else{
                    navigate("/client",{state: user});
                }
            },
            (error)=>{
                alert("Incorrect username or password!");
            }
        );
    }

    function attemptSignUp(e){
        e.preventDefault();
        const signupRequest={username, password};

        auth.register(signupRequest).then((response)=>alert(response.data.message)
        )
    }

    return (
        <div className="App">
            <div className="login-form">
                <h2 className="title">Energy Platform</h2>
                <div className="form">

                    <form>
                        <div className="row-container">
                            <label>Username:

                            </label>
                            <input
                                type="text"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                            />
                        </div>
                        <div className="row-container">
                            <label>Password:
                            </label>
                            <input
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}

                            />
                        </div>
                        <div className="row-container">
                            <button type="login" onClick={attemptLogIn}>Log In</button>
                            <button type="login" onClick={attemptSignUp}>Sign Up</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>


    );
}

export default Login;