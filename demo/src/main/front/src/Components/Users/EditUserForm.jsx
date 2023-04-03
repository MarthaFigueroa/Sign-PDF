import React, {useState, useEffect} from 'react';
import { firestore } from '../../Config/config'
import { UserContext } from "../../Providers/UserProvider";
import { Link } from 'react-router-dom';
import * as IconsFa from "react-icons/fa";
import * as IconsRi from "react-icons/ri";

const EditUserForm = ({editUser, message, id}) => {

    const [values, setValues] = useState({});
    const [user, setUser] = useState([]);

    const handleSubmit = async(e) =>{
        e.preventDefault();
        await editUser(user);
        await message(`Se ha actualizado el usuario: ${user.name}`, "success");
    }

    const blueGradStyles = {
        background: "linear-gradient(223deg, #0356a3, #0464bd, #0472d7, #0383f9, #01a7da)",
        backgroundSize: "400% 400%",
        animation: "gradient 15s ease infinite",
    }

    const handleInputChange = (e) =>{
        const { name, value } = e.target;
        setValues({...values, [name]: value});
        setUser({...user, [name]: value});
    }

    useEffect(() => {
        const getObject = () =>{
            firestore.collection('users').doc(id).get().then(snapshot => setUser(snapshot.data()))
        }
        getObject();
    }, [id]);

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
                                            alt="👨🏻"
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
                                        {/* <div className="input-group">
                                            <label htmlFor="email" className="flex leading-6 text-center mb-6 bg-slate-400 border-none h-12 p-2 rounded-l-lg text-white">
                                            <span className="m-auto px-2 text-white text-xl">
                                                <IconsMd.MdAlternateEmail />
                                            </span>
                                            </label>
                                            <input id="email" type="text" name="email" placeholder="Email" className="h-12 border rounded-r-lg w-full mb-3 leading-6 text-justify pl-1" onChange={handleInputChange} defaultValue={user.email || ""}/>
                                        </div> */}
                                        <UserContext.Consumer>
                                            {currentUser =>{
                                                if(currentUser !== null){
                                                    if(currentUser.role === "admin"){
                                                        return(
                                                            <>
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
                                                                <div className="w-full">
                                                                    <Link type='button' className="
                                                                block px-6 py-2 rounded bg-gray-200 hover:bg-gray-300 transition duration-150 ease-in-out w-full leading-6 text-center cursor-pointer" to="/users">Regresar</Link>
                                                                </div>                   
                                                            </>
                                                        )
                                                    }else{
                                                        return(
                                                            <div className="w-full">
                                                                <Link type='button' className="
                                                            block px-6 py-2 rounded bg-gray-200 hover:bg-gray-300 transition duration-150 ease-in-out w-full leading-6 text-center cursor-pointer" to="/profile">Regresar</Link>
                                                            </div>                   
                                                        )
                                                    }
                                                }
                                            }}
                                        </UserContext.Consumer>
                                        <div className="text-center pt-1 pb-1">
                                                <button
                                                className="block px-6 py-3 text-white font-medium text-xs uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:shadow-lg focus:outline-none focus:ring-0 active:shadow-lg transition duration-150 ease-in-out w-full mb-3 login__btn"
                                                type="submit"
                                                data-mdb-ripple="true"
                                                data-mdb-ripple-color="light"
                                                style={blueGradStyles}
                                                onClick={handleSubmit}
                                                >Modificar</button>                                                
                                            </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default EditUserForm;