import React from 'react';

const NavBar = () =>{
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <a className="navbar-brand" href="/">Firebase Example</a>
            <ul className="navbar-nav mr-auto">
                <li className="nav-item">
                    <a className="nav-link" href="/">List Of Contacts</a>
                </li>
                <li className="nav-item">
                    <a className="nav-link" href="/newLink">Add New Contact</a>
                </li>
                <li className="nav-item">
                    <a className="nav-link" href="/uploadFile">Upload File</a>
                </li>
            </ul>
        </nav>
    )
}

export default NavBar;