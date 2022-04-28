import React, { useState } from 'react'
import { NavLink } from 'react-router-dom'

function Navbar() {
    const [isNavCollapsed, setIsNavCollapsed] = useState(true);

    // const handleNavCollapse = () => setIsNavCollapsed(!isNavCollapsed);

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

  return (
        // <div className="fixed">
        // ${!isNavCollapsed? 'h-14':''} 
            <nav className={`sticky bg-light navbar-expand-md navbar sm:h-20 md:h-20 smd:h-14 lg:h-14`}>
                <button className="navbar-toggler navbar-toggle sm:hidden" type="button" data-toggle="collapse" data-target="#navbarsExample09" aria-controls="navbarsExample09" aria-expanded={!isNavCollapsed ? true : false} aria-label="Toggle navigation" onClick={e => toggleButton(e)}>
                    <span></span>
                    <span></span>
                    <span></span>
                </button>
                {/* <div className="flex items-center"> */}
                {/*  sm:hidden flex absolute right-0 sm:relative  */}
                <div className={`${!isNavCollapsed ? 'block ' : 'hidden'} sm:hidden`} id="mobile-menu">
                    <div className="px-2 pt-2 pb-3 space-y-1 sm:absolute">
                        <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white block'} to="/documents">Listado de Documentos</NavLink>
                        <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white block'} to="/certificates">Listado de Certificados</NavLink>
                        <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white block'} to="/uploadFile">Subir Documento</NavLink>
                        <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white block'} to="/validateDocuments">Validar Documento</NavLink>
                        <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white block'} to="/uploadCert">Cargar Certificado</NavLink>
                    </div>
                </div>
                {/* flex absolute right-0 h-14 bg-light w-full */}
                {/* {`${isNavCollapsed ? 'block ' : 'hidden'} navbar-collapse flex absolute right-0`} */}
                <div className="mobile:hidden flex absolute right-0" id="navbarsExample09">
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white mobile:hidden sm:flex'} to="/documents">Listado de Documentos</NavLink>
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white mobile:hidden sm:flex'} to="/certificates">Listado de Certificados</NavLink>
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white mobile:hidden sm:flex'} to="/uploadFile">Subir Documento</NavLink>
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white mobile:hidden sm:flex'} to="/validateDocuments">Validar Documento</NavLink>
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white mobile:hidden sm:flex'} to="/uploadCert">Cargar Certificado</NavLink>
                </div>
            </nav>
        // </div>
  )
}

export default Navbar