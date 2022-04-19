import React, { useState } from 'react'
import { NavLink } from 'react-router-dom'

function Navbar() {
    const [isNavCollapsed, setIsNavCollapsed] = useState(true);

    // const handleNavCollapse = () => setIsNavCollapsed(!isNavCollapsed);

    const toggleButton = () =>{
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
            <nav className="sticky bg-light navbar-expand-md navbar h-14">
                {/* <a className="" href="/">Validación de Documentos</a> */}
                {/* <div className="max-w-7xl mx-auto px-2 sm:px-6 lg:px-8">
                    <div className="relative flex items-center justify-between h-16">
                    <div className="absolute inset-y-0 left-0 flex items-center sm:hidden">
                        <button type="button" className="inline-flex items-center justify-center p-2 rounded-md text-gray-400 hover:text-white hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white" aria-controls="mobile-menu" aria-expanded="false" onClick={toggleButton}>
                            <span className="sr-only">Open main menu</span>
                            <svg className="block h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" aria-hidden="true">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16M4 18h16" />
                            </svg>
                            <svg className="hidden h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" aria-hidden="true">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
                            </svg>

                        </button>
                    </div>
                    </div>
                </div> */}
                <button className="navbar-toggler navbar-toggle sm:hidden" type="button" data-toggle="collapse" data-target="#navbarsExample09" aria-controls="navbarsExample09" aria-expanded={!isNavCollapsed ? true : false} aria-label="Toggle navigation" onClick={toggleButton}>
                    <span></span>
                    <span></span>
                    <span></span>
                </button>
                {/* <div className="flex items-center"> */}
                <div className="sm:hidden flex absolute right-0 h-14 sm:relative" id="mobile-menu">
                    <div className="px-2 pt-2 pb-3 space-y-1 sm:absolute">
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white block'} to="/documents">List Of Files</NavLink>
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white block'} to="/certificates">List of Certificates</NavLink>
                    {/* <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white block'} to="/documents">List Of Original Documents</NavLink> */}
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white block'} to="/uploadFile">Cargar Documento</NavLink>
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white block'} to="/uploadCert">Cargar Certificado</NavLink>

                    {/* <a href="/" className="bg-gray-900 text-white block px-3 py-2 rounded-md text-base font-medium" aria-current="page">Dashboard</a>
                    <a href="/" className="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium">Team</a>
                    <a href="/" className="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium">Projects</a>
                    <a href="/" className="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium">Calendar</a> */}
                    </div>
                </div>
                <div className={`${isNavCollapsed ? 'collapse ' : ''}navbar-collapse flex absolute right-0 h-14`} id="navbarsExample09">
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white mobile:hidden sm:flex'} to="/documents">List Of Files</NavLink>
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white mobile:hidden sm:flex'} to="/certificates">List of Certificates</NavLink>
                    {/* <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white mobile:hidden sm:flex'} to="/documents">List Of Original Documents</NavLink> */}
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white mobile:hidden sm:flex'} to="/uploadFile">Cargar Documento</NavLink>
                    <NavLink className={({ isActive }) => (isActive ? 'bg-active-item text-white nav-link' : 'inactive nav-link')+' hover:bg-gray-700 p-4 hover:text-white mobile:hidden sm:flex'} to="/uploadCert">Cargar Certificado</NavLink>
                </div>
            </nav>
        // </div>
  )
}

export default Navbar