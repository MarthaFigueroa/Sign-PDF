import React, { useContext, useEffect, useState } from "react";
import { NavLink } from 'react-router-dom';
import { logout } from "../../Config/config";
import { UserContext } from "../../Providers/UserProvider";
import userIcon from './../../public/img/userIcon.png';
import { firestore } from '../../Config/config';
import Uneat from '../../public/img/UNEAT.png'

function Navbar() {
    const [isNavCollapsed, setIsNavCollapsed] = useState(true);
    const [isMenu, setIsMenu] = useState(false);
    const user = useContext(UserContext);
    const [currentUser, setUsers] = useState("");

    function toggleMenu(e) {
        e.preventDefault();
        console.log("Prueba");
        setIsMenu(!isMenu);
    }

    const toggleButton = (e) =>{
        e.preventDefault();
        var toggleNode = document.querySelector("button.navbar-toggle")

        if (toggleNode.classList.contains("is-open")) {
            toggleNode.classList.remove("is-open");
        } else {
            toggleNode.classList.add("is-open");
        }
        setIsNavCollapsed(!isNavCollapsed);
    }

    useEffect(() => {
        if(user){
            // console.log(user);
            firestore.collection("users").where("uid", "==", user.uid).get()
            .then(async querySnapshot => {
                const matchedDocs = querySnapshot.size;
                const usersArr = [];
                querySnapshot.forEach(user => {
                    usersArr.push({...user.data(), id: user.id});
                });
                if (matchedDocs) {
                    console.log(usersArr[0]);
                    setUsers(usersArr[0]);
                }
            })
        }
    }, [user])

  return (
            <nav className={`sticky bg-light navbar-expand-md navbar sm:h-20 md:h-20 smd:h-16 lg:h-34`}>
                <button className="navbar-toggler navbar-toggle sm:hidden" type="button" data-toggle="collapse" data-target="#navbarsExample09" aria-controls="navbarsExample09" aria-expanded={!isNavCollapsed ? true : false} aria-label="Toggle navigation" onClick={e => toggleButton(e)}>
                    <span></span>
                    <span></span>
                    <span></span>
                </button>

                {/* Mobile View */}
                <div className={`${isNavCollapsed ? 'hidden ' : 'block '} sm:hidden`} id="mobile-menu">
                    <a href="/" className="flex items-center">
                        <img src={Uneat} className="mr-3 h-16 sm:h-16" alt="UNEATLANTICO" />
                    </a>
                    <div className="px-2 pt-2 pb-3 space-y-1 sm:absolute">
                        <UserContext.Consumer>
                            {user =>{
                                if(user !== null){
                                    if(user.role === 'admin'){
                                        return(
                                            <>
                                                <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-[#004F79] p-4 hover:text-white block'} to="/users">Listado de Usuarios</NavLink>
                                                <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-[#004F79] p-4 hover:text-white block'} to="/certificates">Listado de Certificados</NavLink>
                                                <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-[#004F79] p-4 hover:text-white block'} to="/uploadCert">Subir Certificado</NavLink>
                                            </>
                                        )
                                    }
                                }
                            }}
                        </UserContext.Consumer>
                        <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-[#004F79] p-4 hover:text-white block'} to="/documents">Listado de Documentos</NavLink>
                        <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-[#004F79] p-4 hover:text-white block'} to="/uploadFile">Firmar Documento</NavLink>
                        <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-[#004F79] p-4 hover:text-white block'} to="/validateDocuments">Validar Documento</NavLink>
                        <UserContext.Consumer>
                            {user =>{
                                if(user !== null){
                                    return(
                                        <>
                                            <button type="button" className="flex absolute justify-center justify-items-center text-center my-auto mr-3 text-sm rounded-full md:mr-0 top-0 right-0 " id="user-menu-button" aria-expanded="false" data-dropdown-toggle="dropdown">
                                                <img className="w-8 rounded-full" src={user.photoURL !== null ? user.photoURL : userIcon} alt="👨🏻" />
                                            </button>
                                            <div className={`block my-4 list-none rounded shadow dark:bg-gray-700 dark:divide-gray-600 absolute top-10 right-1 xs:w-56 container`} id="dropdown">
                                                <div className="py-3 px-4">
                                                    <span className="block text-sm text-gray-900 dark:text-white">{currentUser.name}</span>
                                                    <span className="block text-sm font-medium text-gray-500 truncate dark:text-gray-400">{user.email}</span>
                                                </div>
                                                <ul className="py-1" aria-labelledby="dropdown">
                                                    <li>
                                                        <a href="/profile" className="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Perfil</a>
                                                    </li>
                                                    <li>
                                                        <button className="w-full block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white" onClick={logout}>Finalizar Sesión</button>
                                                    </li>
                                                </ul>
                                            </div>
                                        </>
                                    )
                                }else{
                                    return(
                                        <button type="button" className="flex justify-center justify-items-center text-center my-auto mr-3 text-sm rounded-full md:mr-0 " id="user-menu-button" aria-expanded="false" data-dropdown-toggle="dropdown">
                                            <img className="w-8 rounded-full" src={userIcon} alt="👨🏻" />
                                        </button>
                                    )
                                }
                            }}
                        </UserContext.Consumer>
                    </div>
                </div>
                
                {/* Expand Window */}
                <div className="mobile:hidden" id="navbarsExample09">
                <a href="/" className="flex absolute left-0 hidden lg:block xl:block">
                    <img src={Uneat} className="mr-3 h-14 sm:h-14" alt="UNEATLANTICO" />
                </a>
                <div className="flex absolute right-0 h-16">
                    <UserContext.Consumer>
                        {user =>{
                            if(user !== null){
                                if(user.role === 'admin'){
                                    return(
                                        <>
                                            <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-[#004F79] p-4 hover:text-white mobile:hidden sm:flex'} to="/users">Listado de Usuarios</NavLink>
                                            <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-[#004F79] p-4 hover:text-white mobile:hidden sm:flex'} to="/certificates">Listado de Certificados</NavLink>
                                            <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-[#004F79] p-4 hover:text-white mobile:hidden sm:flex'} to="/uploadCert">Subir Certificado</NavLink>
                                        </>
                                    )
                                }
                            }
                        }}
                    </UserContext.Consumer>
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-[#004F79] p-4 hover:text-white mobile:hidden sm:flex'} to="/documents">Listado de Documentos</NavLink>
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-[#004F79] p-4 hover:text-white mobile:hidden sm:flex'} to="/uploadFile">Firmar Documento</NavLink>
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-[#004F79] p-4 hover:text-white mobile:hidden sm:flex'} to="/validateDocuments">Validar Documento</NavLink>
                    {/* Dropdown menu */}
                    <UserContext.Consumer>
                        {user =>{
                            if(user !== null){
                                return(
                                    <>
                                    <button type="button" className="flex w-8 justify-center justify-items-center text-center my-auto mr-3 rounded-full md:mr-0" id="user-menu-button" aria-expanded="false" data-dropdown-toggle="dropdown">
                                        {/* <span className="sr-only">Open user menu</span> */}
                                        <img width={32} className="rounded-full" src={user.photoURL ? user.photoURL : userIcon} alt="👨🏻"  onClick={(e)=>toggleMenu(e)}/>
                                    </button>
                                        <div className={`${isMenu ? 'block ' : 'hidden'} my-4 list-none rounded shadow dark:bg-gray-700 dark:divide-gray-600 absolute top-10 right-1`} id="dropdown">
                                            <div className="py-3 px-4">
                                                <span className="block text-sm text-gray-900 dark:text-white">{currentUser.name}</span>
                                                <span className="block text-sm font-medium text-gray-500 truncate dark:text-gray-400">{user.email}</span>
                                            </div>
                                            <ul className="py-1" aria-labelledby="dropdown">
                                                <li>
                                                    <a href="/profile" className="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">Perfil</a>
                                                </li>
                                                <li>
                                                    <button className="w-full block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white" onClick={logout}>Finalizar Sesión</button>
                                                </li>
                                            </ul>
                                        </div>
                                    </>
                                )
                            }else{
                                return(
                                    <button type="button" className="flex w-8 justify-center justify-items-center text-center my-auto mr-3 rounded-full md:mr-0" id="user-menu-button" aria-expanded="false" data-dropdown-toggle="dropdown">
                                        <img width={32} className="rounded-full" src={userIcon} alt="user" />
                                    </button>
                                )
                            }
                        }}
                    </UserContext.Consumer>
                </div>
                </div>
            </nav>
  )
}

export default Navbar