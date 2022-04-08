import React, { useState } from 'react';
import { NavLink } from 'react-router-dom';

const NavBar = () =>{
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
        <nav className="navbar navbar-expand-md navbar-light bg-light ">
            <a className="navbar-brand font-weight-bolder" href="/documents">Validación de Documentos</a>
            {/* <button className="custom-toggler navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample09" aria-controls="navbarsExample09" aria-expanded={!isNavCollapsed ? true : false} aria-label="Toggle navigation" onClick={handleNavCollapse}>
                <span className="navbar-toggler-icon"></span>
            </button> */}
            <button className="custom-toggler navbar-toggler navbar-toggle" type="button" data-toggle="collapse" data-target="#navbarsExample09" aria-controls="navbarsExample09" aria-expanded={!isNavCollapsed ? true : false} aria-label="Toggle navigation" onClick={toggleButton}>
                <span></span>
                <span></span>
                <span></span>
            </button>
            <div className={`${isNavCollapsed ? 'collapse' : ''} navbar-collapse`} id="navbarsExample09">
                <NavLink className={({ isActive }) => (isActive ? 'active nav-link' : 'inactive nav-link')} to="/">List Of Contacts</NavLink>
                <NavLink className={({ isActive }) => (isActive ? 'active nav-link' : 'inactive nav-link')} to="/cards">List Of Files</NavLink>
                <NavLink className={({ isActive }) => (isActive ? 'active nav-link' : 'inactive nav-link')} to="/newLink">Add New Contact</NavLink>
                <NavLink className={({ isActive }) => (isActive ? 'active nav-link' : 'inactive nav-link')} to="/documents">List Of Original Documents</NavLink>
                <NavLink className={({ isActive }) => (isActive ? 'active nav-link' : 'inactive nav-link')} to="/signedDocuments">List Of Signed Documents</NavLink>
                <NavLink className={({ isActive }) => (isActive ? 'active nav-link' : 'inactive nav-link')} to="/uploadFile">Upload File</NavLink>
            </div>
        </nav>

  
        // <div class={`${isNavCollapsed ? 'collapse' : ''} navbar-collapse`} id="navbarsExample09">
        //   <a className="nav-link text-info" href="/contact">Support</a>
        //   <a className="nav-link text-info" href="/login">Login</a>
        //   <a href="/request-demo" className="btn btn-sm btn-info nav-link text-white" >Request demo</a>
        // </div>
    )
}

export default NavBar;