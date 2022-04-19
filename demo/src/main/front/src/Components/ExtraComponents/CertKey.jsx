import {React, useState} from "react";
import * as Comp from "@material-ui/core";
import * as Icon from "@material-ui/icons";
// import InputLabel from "@material-ui/core/InputLabel";
// import InputAdornment from "@material-ui/core/InputAdornment";
// import VisibilityOff from "@material-ui/icons/VisibilityOff";
// import Input from "@material-ui/core/Input";

const CertKey = (props) => {
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
	<div>
	<Comp.InputLabel htmlFor="standard-adornment-password">
		Enter your Password
	</Comp.InputLabel>
	<Comp.Input
        id="cert-pass-selector"
		type={values.showPassword ? "text" : "password"}
        className="passInput"
		onChange={handlePasswordChange}
		endAdornment={
		<Comp.InputAdornment position="end">
			<Comp.IconButton
			onClick={handleClickShowPassword}
			onMouseDown={handleMouseDownPassword}
			>
			{values.showPassword ? <Icon.Visibility /> : <Icon.VisibilityOff />}
			</Comp.IconButton>
		</Comp.InputAdornment>
		}
	/>
	</div>
);
};

export default CertKey;
