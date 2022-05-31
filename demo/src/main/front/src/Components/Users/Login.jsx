import React, { useEffect, useState, useContext } from 'react'
import logo from "../../public/img/UNEAT.png";
import googleIcon from "../../public/img/googleIcon.png";
import * as Icon from "@material-ui/icons";
import { Link, useNavigate } from "react-router-dom";
import { logInWithEmailAndPassword, signInWithGoogle } from "../../Config/config"; //auth
import { toast } from 'react-toastify';
import { UserContext } from '../../Providers/UserProvider';

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    // const [user, loading] = useAuthState(auth);
    const navigate = useNavigate();
    const currentUser = useContext(UserContext);

    const [values, setValues] = useState({
        password: "",
        showPassword: false,
    });
    
    const handleClickShowPassword = () => {
        setValues({ ...values, showPassword: !values.showPassword });
    };
    
    const handleMouseDownPassword = (event) => {
        event.preventDefault();
    };
    
    // const handlePasswordChange = (event) => {
    //     const pass = document.getElementById(event.target.id).value;
    //     console.log(pass);
    //     // props.handlePass(pass);
    //     // setValues({ ...values, [prop]: event.target.value });
    // };
    const messageAlert = async (msg, type) =>{
        console.log(msg);
            toast(msg, {
            type: type,
            autoClose: 2000
        });
    }

    useEffect(() => {
        console.log("User", currentUser);
        if (currentUser === null) {
          navigate("/");
            // if(error.message === 'Firebase: Error (auth/user-disabled).'){
            //     messageAlert("Este usuario ha sido deshabilitado.", "error")
            // }
          return;
        }
        if (currentUser !== null) {
            console.log("ojfknc",currentUser);
            navigate("/documents");
        }
    }, [currentUser, navigate]); //loading

  return (
    <>
    {/* md:h-screen */}
        {/* <section className="w-full gradient-form bg-gray-200 flex justify-center"> */}
            <div className="h-screen">
                <div className="flex flex-wrap g-6 text-gray-800 content-center py-12 px-6 xl:h-full md:h-auto">
                <div className="xl:w-10/12 mx-auto">
                    <div className="block bg-white shadow-lg rounded-lg h-4/5 lg:flex lg:flex-wrap g-0">
                        {/* <div className="lg:flex lg:flex-wrap g-0"> */}
                            {/*  lg:rounded-r-lg rounded-b-lg lg:rounded-bl-none */}
                            <div className="lg:rounded-l-lg lg:rounded-tr-none lg:w-6/12 flex items-center sm:rounded-t-lg rounded-t-lg login">
                                <div className="text-white px-4 py-6 md:p-12 md:mx-6">
                                    <h4 className="text-xl font-semibold mb-6">Sistema de Validación y Gestión Digitalizada de Documentos PDF</h4>
                                    <p className="text-sm">
                                    Este Sistema permite al usuario la firma y validacion de documentos electrónicos en formato PDF, con el fin de garantizar la autenticidad e integridad de la información que se está manejando.
                                    </p>
                                </div>
                            </div>
                            <div className="lg:w-6/12 px-4 md:px-0 bg-white">
                                <div className="md:p-12 md:mx-6">
                                    <div className="text-center pt-2">
                                        <img
                                            className="mx-auto w-2/4 mb-12 sm:max-w-11/12"
                                            src={logo}
                                            alt="logo"
                                        />
                                        {/* <h4 className="text-xl font-semibold mt-4 mb-12 pb-1">Sistema de Validación y Gestión Digitalizada de Documentos PDF</h4> */}
                                    </div>
                                    <form>
                                        <div className="input-group">
                                            {/* input-group-text */}
                                            <label htmlFor="user" className="flex leading-6 text-center mb-6 bg-slate-400 border-none h-12 p-2 rounded-l-lg text-white">
                                                {/* <i className="material-icons m-auto">person</i> */}
                                            <span className="m-auto px-2 h-12 text-white">
                                                <Icon.Person />
                                            </span>
                                            </label>
                                            <input id="user" type="text" name="usuario" placeholder="Usuario" className="h-12 border rounded-r-lg w-full mb-3 leading-6 text-justify pl-1" value={email} onChange={(e) => setEmail(e.target.value)}/>
                                        </div>
                                        <div className="input-group">
                                            {/* input-group-text */}
                                            <label htmlFor="password" className="flex leading-6 text-center mb-6 bg-slate-400 border-none h-12 p-2 rounded-l-lg text-white">
                                                {/* <i className="material-icons m-auto">lock</i> */}
                                                <span className="m-auto px-2 h-12 text-white">
                                                    <Icon.Lock />
                                                </span>
                                            </label>
                                            {/* <input type="password" name="password" placeholder="Contraseña" className="h-12 border rounded-r-lg w-full mb-3 leading-6 text-justify pl-1"/> */}

                                            <span className="absolute inset-y-0 right-0 bottom-0 flex items-center px-2 h-12 text-slate-400">
                                                <button className="MuiButtonBase-root MuiIconButton-root" tabIndex="0" type="button"onClick={handleClickShowPassword}
                                                onMouseDown={handleMouseDownPassword}>
                                                {/* <i className="material-icons m-auto">{values.showPassword ? "visibility":<VisibilityOff />}</i> */}
                                                {values.showPassword ? <Icon.Visibility /> : <Icon.VisibilityOff />}
                                                </button>
                                                
                                            </span>
                                            <input id="password" placeholder='Contraseña' type={values.showPassword ? "text" : "password"} className="h-12 border rounded-r-lg w-full mb-3 leading-6 text-justify pl-1" value={password}onChange={(e) => setPassword(e.target.value)}
                                            />

                                        </div>
                                            {/* <input
                                            type="text"
                                            className="form-control block w-full px-3 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
                                            id="exampleFormControlInput1"
                                            placeholder="Username"
                                            /> */}
                                        {/* </div> */}
                                        <div className="text-center pt-1 mb-12 pb-1">
                                            <button
                                            // inline-block px-6 py-2.5 text-white font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:shadow-lg transition duration-150 ease-in-out w-full mb-3
                                            className="block px-6 py-3 text-white font-medium text-xs uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:shadow-lg transition duration-150 ease-in-out w-full mb-3 login"
                                            type="button"
                                            data-mdb-ripple="true"
                                            data-mdb-ripple-color="light"
                                            onClick={() => logInWithEmailAndPassword(email, password)}
                                            >Log in</button>
                                            <button className="block px-6 py-3 text-white font-medium text-xs uppercase rounded shadow-md hover:bg-slate-500 hover:text-white hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:shadow-lg transition duration-150 ease-in-out w-full mb-3 bg-slate-400"
                                            type="button" onClick={signInWithGoogle}>
                                                {/*  right-0 lg:right-0 smd:right-0 md:right-12 */}
                                                <div className="mx-auto relative w-3/6">
                                                    Login with Google 
                                                    <img className='inline absolute ml-1' src={googleIcon} alt="" width='16'/>
                                                </div>
                                            </button>
                                            <Link to={'/documents'} className="w-full block px-6 py-3 text-white font-medium text-xs uppercase rounded shadow-md hover:bg-sky-400 hover:text-white hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:shadow-lg transition duration-150 ease-in-out w-full mb-3 guest"
                                            type="button">
                                                {/*  right-0 lg:right-0 smd:right-0 md:right-12 */}
                                                <div className="mx-auto relative w-3/6">
                                                    Continue as a Guest
                                                </div>
                                            </Link>
                                            {/* <a className="text-gray-500" href="#!">Forgot password?</a> */}
                                            <div className='italic'>
                                                <Link to="/reset">Forgot Password?</Link>
                                            </div>
                                            <div>
                                                Don't have an account? <Link className='underline' to="/register">Register</Link> now.
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>                            
                        {/* </div> */}
                    </div>
                </div>
                </div>
            </div>
        {/* </section> */}
    </>
  )
}

export default Login