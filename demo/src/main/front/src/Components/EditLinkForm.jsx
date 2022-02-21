import React, {useState, useEffect} from 'react';
// import 'bootswatch/dist/minty/bootstrap.min.css';

const EditLinkForm = (props) => {

    const initialStateValues = {
        firstname: "",
        lastname: "",
        email: "",
        phone: ""
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

    useEffect(() => {
        console.log("GG",props.object);
        setValues(props.object);
    }, [props.object]);

    return(
        <>            
            <form onSubmit={handleSubmit}>
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">person</i>
                    </div>
                    <input type="text" name="firstname" placeholder="Firstname" className="form-control" onChange={handleInputChange} defaultValue={values.firstname || ""} autoFocus />
                    <input type="text" name="lastname" placeholder="Lastname" className="form-control" onChange={handleInputChange} defaultValue={values.lastname || ""} />
                </div>
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">email</i>
                    </div>
                    <input type="text" name="email" placeholder="Email" className="form-control" onChange={handleInputChange} defaultValue={values.email || ""} />
                </div>
                <div className="form-group input-group formField">
                    <div className="input-group-text icon bg-light">
                        <i className="material-icons">phone</i>
                    </div>
                    <input type="text" name="phone" placeholder="Phone" className="form-control" onChange={handleInputChange} defaultValue={values.phone || ""} />
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

export default EditLinkForm;