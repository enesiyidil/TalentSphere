import PropTypes from "prop-types";
import "../Css/Card.css";
import { useContext, useState } from "react";
import { ApiContext } from "../context/ApiContext";

const PersonalCard = ({ personal }) => {
    const [editPersonal, setEditPersonal] = useState("");
    const [name, setName] = useState(personal.name);
    const [surname, setSurname] = useState(personal.surname);
    const [email, setEmail] = useState(personal.email);
    const [phone, setPhone] = useState(personal.phone);
    const [title, setTitle] = useState(personal.title);
    const [photo, setPhoto] = useState(personal.photo);
    const [salary, setSalary] = useState(personal.salary);

    const [isDeletedLoading, setIsDeletedLoading] = useState(false);
    // const { deletePersonal, updateRecipes } = useContext(ApiContext);

    const handleUpdate = (e) => {
        console.log(editPersonal);
        e.preventDefault();
        setEditPersonal("");
    };
    return (
        <>
            {editPersonal ? (
                <div className="recipe-card">
                    <form
                        className="update-recipe-form"
                        onSubmit={(e) => handleUpdate(e)}
                    >
                        <p>Image URL</p>
                        <input
                            onChange={(e) => setPhoto(e.target.value)}
                            value={photo}
                            type="text"
                        />
                        <p>Title</p>
                        <input
                            onChange={(e) => setTitle(e.target.value)}
                            value={title}
                            type="text"
                        />
                        <p>Name</p>
                        <input
                            onChange={(e) => setName(e.target.value)}
                            value={name}
                            type="text"
                        />
                        <p>Surname</p>
                        <input
                            onChange={(e) => setSurname(e.target.value)}
                            value={surname}
                            type="text"
                        />
                        <p>Phone</p>
                        <input
                            onChange={(e) => setPhone(e.target.value)}
                            value={phone}
                            type="text"
                        />
                        <p>Email</p>
                        <input
                            onChange={(e) => setEmail(e.target.value)}
                            value={email}
                            type="text"
                        />
                        <p>Salary</p>
                        <input
                            onChange={(e) => setSalary(e.target.value)}
                            value={salary}
                            type="text"
                        />

                        <button type="submit">Update</button>
                        <button type="button" onClick={() => setEditPersonal("")}>
                            Cancel
                        </button>
                    </form>
                </div>
            ) : (
                <div className="recipe-card">
                    <img src={personal.photo} alt=""/>
                    <h3>{personal.title}</h3>
                    <p>{personal.name}</p>
                    <p>{personal.surname}</p>
                    <p>{personal.phone}</p>
                    <p>{personal.email}</p>
                    <p>{personal.salary}</p>
                    <div>
                        <button
                            className="delete-button"
                            onClick={async () => {
                                setIsDeletedLoading(true);
                                // await deleteRecipe(recipe.id);
                                setIsDeletedLoading(true);
                            }}
                        >
                            {isDeletedLoading ? "Loading..." : "X"}
                        </button>
                        <button
                            className="update-button"
                            onClick={() => setEditPersonal(personal)}
                        >
                            U
                        </button>
                    </div>
                </div>
            )}
        </>
    );
};

//burası hata görüntüü olmasın diye gerekli değil
PersonalCard.propTypes = {
    personal: PropTypes.object
};

export default PersonalCard;