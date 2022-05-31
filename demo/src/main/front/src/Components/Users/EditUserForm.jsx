import React, {useState, useEffect} from 'react';
import { firestore } from '../../Config/config'
import { Link } from 'react-router-dom';
// import * as Icon from "@material-ui/icons";
import * as IconsFa from "react-icons/fa";
import * as IconsRi from "react-icons/ri";
import * as IconsMd from "react-icons/md";

const EditUserForm = (props) => {

    // const [email, setEmail] = useState("");
    // const [password, setPassword] = useState("");
    // const [name, setName] = useState("");
    // const [role, setRole] = useState("");
    const [values, setValues] = useState({});

    // const navigate = useNavigate();

    // const goTo = (route) =>{
    //     console.log("kkk");
    //     navigate(route);
    // }

    const [user, setUser] = useState([]);

    const handleSubmit = async(e) =>{
        e.preventDefault();
        console.log(user);
        await props.editUser(user);
        // await setUser({values});
        // goTo('/users');
        await props.message(`Se ha actualizado el usuario: ${user.name}`, "success");
    }

    const blueGradStyles = {
        background: "linear-gradient(223deg, #0356a3, #0464bd, #0472d7, #0383f9, #01a7da)",
        backgroundSize: "400% 400%",
        animation: "gradient 15s ease infinite",
    }

    const handleInputChange = (e) =>{
        const { name, value } = e.target;
        console.log(name, value);
        setValues({...values, [name]: value});
        setUser({...user, [name]: value});
    }

    useEffect(() => {
        const getObject = () =>{
            console.log(props.id);
            firestore.collection('users').doc(props.id).get().then(snapshot => setUser(snapshot.data()))
        }
        getObject();
    }, [props.id]);

    return(
        <>  
            <div className="h-screen w-full">
                {/* flex flex-wrap g-6 text-gray-800 content-center py-12 px-6 xl:h-full md:h-auto */}
                <div className="">
                    <div className="w-full">
                        {/*  h-4/5 lg:flex lg:flex-wrap g-0 */}
                        <div className="block bg-white shadow-lg rounded-lg">
                            <div className="w-full px-4 md:px-0">
                                <div className="md:p-12 md:mx-6">
                                    <div className="text-center mb-12">
                                        <img
                                            className="mx-auto userImg xl:w-1/2 lg:w-1/2 md:w-1/2 "
                                            src={user.photoURL}
                                            alt=""
                                        />
                                        {/* <h4 className="text-xl font-semibold mt-4 mb-12 pb-1">Sistema de Validación y Gestión Digitalizada de Documentos PDF</h4> */}
                                    </div>
                                    <form onSubmit={handleSubmit}>
                                        <div className={`input-group`}>
                                            {/* input-group-text */}
                                            <label htmlFor="user" className="flex leading-6 text-center mb-6 bg-slate-400 border-none h-12 p-2 rounded-l-lg text-white">
                                                {/* <i className="material-icons m-auto">person</i> */}
                                                <span className="m-auto px-2 text-white text-xl">
                                                    <IconsFa.FaUserAlt />
                                                </span>
                                            </label>
                                            <input id="user" type="text" name="name" placeholder="Nombre Completo" className="h-12 border rounded-r-lg w-full mb-3 leading-6 text-justify pl-1" onChange={handleInputChange} defaultValue={user.name || ""}/>
                                        </div>
                                        <div className="input-group">
                                            {/* input-group-text */}
                                            <label htmlFor="email" className="flex leading-6 text-center mb-6 bg-slate-400 border-none h-12 p-2 rounded-l-lg text-white">
                                                {/* <i className="material-icons m-auto">person</i> */}
                                            <span className="m-auto px-2 text-white text-xl">
                                                <IconsMd.MdAlternateEmail />
                                            </span>
                                            </label>
                                            <input id="email" type="text" name="email" placeholder="Email" className="h-12 border rounded-r-lg w-full mb-3 leading-6 text-justify pl-1" onChange={handleInputChange} defaultValue={user.email || ""}/>
                                        </div>
                                        <div className="input-group">
                                            {/* input-group-text */}
                                            <label htmlFor="role" className="flex leading-6 text-center mb-6 bg-slate-400 border-none h-12 p-2 rounded-l-lg text-white">
                                                {/* <i className="material-icons m-auto">person</i> */}
                                            <span className="m-auto px-2 text-white text-xl">
                                                <IconsRi.RiUserSettingsFill />
                                            </span>
                                            </label>
                                            <input id="role" type="text" name="role" placeholder="Rol del usuario" className="h-12 border rounded-r-lg w-full mb-3 leading-6 text-justify pl-1" onChange={handleInputChange} defaultValue={user.role || ""}/>
                                        </div>
                                        <div className="text-center pt-1 pb-1">
                                            <button
                                            className="block px-6 py-3 text-white font-medium text-xs uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:shadow-lg transition duration-150 ease-in-out w-full mb-3 login__btn"
                                            type="submit"
                                            data-mdb-ripple="true"
                                            data-mdb-ripple-color="light"
                                            style={blueGradStyles}
                                            onClick={handleSubmit}
                                            >Modificar</button>
                                            <div className="w-full">
                                                <Link type='button' className="
                                               block px-6 py-3 rounded bg-gray-200 hover:bg-gray-300 transition duration-150 ease-in-out w-full leading-6 text-center cursor-pointer" to="/profile">Cancel</Link>
                                            </div>

                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
                {/* <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">person</i>
                    </div>
                    <input type="text" name="firstname" placeholder="Firstname" className="form-control" onChange={handleInputChange} value={values.firstname || ""} />
                    <input type="text" name="lastname" placeholder="Lastname" className="form-control" onChange={handleInputChange} value={values.lastname || ""} />
                </div>
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">email</i>
                    </div>
                    <input type="text" name="email" placeholder="Email" className="form-control" onChange={handleInputChange} value={values.email || ""} />
                </div>
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">phone</i>
                    </div>
                    <input type="text" name="phone" placeholder="Phone" className="form-control" onChange={handleInputChange} value={values.phone || ""} />
                </div>
                <div className="mx-auto text-center">
                    <button type="submit" className="btn btn-primary" onClick={handleSubmit}>Update</button>
                </div>
                <Link className="btn btn-light" to="/">Cancel</Link> */}
            
            {/* <form onSubmit={handleSubmit}>
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">person</i>
                    </div>
                    <input type="text" name="firstname" placeholder="Firstname" className="form-control" onChange={handleInputChange} value={values.firstname || ""} autoFocus />
                    <input type="text" name="lastname" placeholder="Lastname" className="form-control" onChange={handleInputChange} value={values.lastname || ""} />
                </div>
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">email</i>
                    </div>
                    <input type="text" name="email" placeholder="Email" className="form-control" onChange={handleInputChange} value={values.email || ""} />
                </div>
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">phone</i>
                    </div>
                    <input type="text" name="phone" placeholder="Phone" className="form-control" onChange={handleInputChange} value={values.phone || ""} />
                </div>
                <div className="form-group mx-auto text-center">
                    <button className="btn btn-primary">Add</button>
                    <Link className="btn btn-info" to="/">Cancel</Link>
                </div>
            </form> */}
            {/* <button className="btn btn-info" onClick={()=>goTo('/')}>cancel</button> */}
        </>
    )
}

export default EditUserForm;