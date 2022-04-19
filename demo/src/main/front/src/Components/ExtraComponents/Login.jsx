import React, { useState } from 'react'
import logo from "../../public/img/UNEAT.png";
import * as Icon from "@material-ui/icons";

function Login() {
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
    
    const handlePasswordChange = (event) => {
        const pass = document.getElementById(event.target.id).value;
        console.log(pass);
        // props.handlePass(pass);
        // setValues({ ...values, [prop]: event.target.value });
    };

    const blueGradStyles = {
        background: "linear-gradient(223deg, #0356a3, #0464bd, #0472d7, #0383f9, #01a7da)",
	    backgroundSize: "400% 400%",
        animation: "gradient 15s ease infinite",
    }
  return (
    <>
        <section className="w-full h-full gradient-form bg-gray-200 md:h-screen flex justify-center">
            <div className="container py-12 px-6 h-full">
                <div className="flex justify-center items-center flex-wrap h-full g-6 text-gray-800">
                <div className="xl:w-10/12">
                    <div className="block bg-white shadow-lg rounded-lg">
                    <div className="lg:flex lg:flex-wrap g-0">
                        {/*  lg:rounded-r-lg rounded-b-lg lg:rounded-bl-none */}
                        <div className="lg:rounded-l-lg lg:rounded-tr-none lg:w-6/12 flex items-center sm:rounded-t-lg rounded-t-lg" style={blueGradStyles}>
                            <div className="text-white px-4 py-6 md:p-12 md:mx-6">
                                <h4 className="text-xl font-semibold mb-6">Sistema de Validación y Gestión Digitalizada de Documentos PDF</h4>
                                <p className="text-sm">
                                Este Sistema permite al usuario la firma y validacion de documentos electrónicos en formato PDF, con el fin de garantizar la autenticidad e integridad de la información que se está manejando.
                                </p>
                            </div>
                        </div>
                        <div className="lg:w-6/12 px-4 md:px-0">
                            <div className="md:p-12 md:mx-6">
                                <div className="text-center pt-2">
                                    <img
                                        className="mx-auto w-54 mb-12 sm:max-w-11/12"
                                        src={logo}
                                        alt="logo"
                                    />
                                    {/* <h4 className="text-xl font-semibold mt-4 mb-12 pb-1">Sistema de Validación y Gestión Digitalizada de Documentos PDF</h4> */}
                                </div>
                                <form>
                                    {/* <div className="mb-4"> */}
                                    {/* form-group input-group formField */}
                                    {/* flex flex-nowrap border-none relative items-stretch w-full mb-4 */}
                                    <div className="input-group">
                                        {/* input-group-text */}
                                        <label htmlFor="user" className="flex leading-6 text-center mb-6 bg-slate-400 border-none h-12 p-2 rounded-l-lg text-white">
                                            {/* <i className="material-icons m-auto">person</i> */}
                                        <span className="m-auto px-2 h-12 text-white">
                                            <Icon.Person />
                                        </span>
                                        </label>
                                        <input id="user" type="text" name="usuario" placeholder="Usuario" className="h-12 border rounded-r-lg w-full mb-3 leading-6 text-justify pl-1"/>
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
                                        <input id="password" placeholder='Contraseña' type={values.showPassword ? "text" : "password"} className="h-12 border rounded-r-lg w-full mb-3 leading-6 text-justify pl-1" onChange={handlePasswordChange}/>

                                    </div>
                                        {/* <input
                                        type="text"
                                        className="form-control block w-full px-3 py-1.5 text-base font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-blue-600 focus:outline-none"
                                        id="exampleFormControlInput1"
                                        placeholder="Username"
                                        /> */}
                                    {/* </div> */}
                                    <div className="text-center pt-1 mb-12 pb-1">
                                        <a
                                        href='/documents'
                                        // inline-block px-6 py-2.5 text-white font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:shadow-lg transition duration-150 ease-in-out w-full mb-3
                                        className="block px-6 py-3 text-white font-medium text-xs uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:shadow-lg transition duration-150 ease-in-out w-full mb-3"
                                        type="button"
                                        data-mdb-ripple="true"
                                        data-mdb-ripple-color="light"
                                        style={blueGradStyles}
                                        >Log in</a>
                                        <a className="text-gray-500" href="#!">Forgot password?</a>
                                    </div>
                                </form>
                            </div>
                        </div>
                        
                    </div>
                    </div>
                </div>
                </div>
            </div>
            </section>
    </>
  )
}

export default Login