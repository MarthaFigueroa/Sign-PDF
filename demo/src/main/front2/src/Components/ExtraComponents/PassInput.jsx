import React, { useState } from 'react';
import * as Icon from "@material-ui/icons";

function PassInput(props) {
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
        const pass = document.getElementById("cert-pass-selector").value;
        console.log(pass);
        props.handlePass(pass);
        // setValues({ ...values, [prop]: event.target.value });
    };
  return (
    <div className="input-group">
        <label htmlFor="password" className="flex leading-6 text-center mb-6 bg-icon-group border-none h-12 p-2 rounded-l-lg text-white">
            <span className="m-auto px-2 h-12 text-white">
                <Icon.Lock />
            </span>
        </label>

        <span className="absolute inset-y-0 right-0 bottom-0 flex items-center px-2 h-12 text-slate-400">
            <button className="MuiButtonBase-root MuiIconButton-root" tabIndex="0" type="button"onClick={handleClickShowPassword}
            onMouseDown={handleMouseDownPassword}>
            {/* <i className="material-icons m-auto">{values.showPassword ? "visibility":<VisibilityOff />}</i> */}
            {values.showPassword ? <Icon.Visibility /> : <Icon.VisibilityOff />}
            </button>
            
        </span>
        <input id="cert-pass-selector" type={values.showPassword ? "text" : "password"} className="h-12 border rounded-r-lg w-full mb-3 leading-6 text-justify pl-1" onChange={handlePasswordChange}/>
    </div>


    // </div>
  )
}

export default PassInput