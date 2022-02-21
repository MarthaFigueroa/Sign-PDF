import React, {useState} from 'react';
// import 'bootswatch/dist/minty/bootstrap.min.css';

const LinkForm = (props) => {

    const initialStateValues = {
        firstname: '',
        lastname: '',
        email: '',
        phone: ''
    }

    const [values, setValues] = useState(initialStateValues);

    const handleSubmit = (e) =>{
        e.preventDefault();
        props.addOrEditLink(values);
        setValues({...initialStateValues});
    }

    const handleInputChange = (e) =>{
        const { name, value } = e.target;
        setValues({...values, [name]: value});
    }

    return(
        <>            
            <form onSubmit={handleSubmit}>
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">person</i>
                    </div>
                    <input type="text" name="firstname" placeholder="Firstname" className="form-control" onChange={handleInputChange} value={values.firstname} autoFocus />
                    <input type="text" name="lastname" placeholder="Lastname" className="form-control" onChange={handleInputChange} value={values.lastname} autoFocus />
                </div>
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">email</i>
                    </div>
                    <input type="text" name="email" placeholder="Email" className="form-control" onChange={handleInputChange} value={values.email} autoFocus />
                </div>
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">phone</i>
                    </div>
                    <input type="text" name="phone" placeholder="Phone" className="form-control" onChange={handleInputChange} value={values.phone} autoFocus />
                </div>
                <div className="form-group mx-auto text-center">
                    <button className="btn btn-primary">
                        Add
                    </button>
                </div>
            </form>
        </>
    )
}

export default LinkForm;