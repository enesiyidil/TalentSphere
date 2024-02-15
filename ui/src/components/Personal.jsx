import {useSelector} from "react-redux";
import PersonalCard from "./PersonalCard.jsx";

export const Personal = () => {
    const data = useSelector((state) => state.data);
    return (<>
        {data.personals.map(personal => (<PersonalCard key={personal.id} personal={personal} /> ))}

    </>)
}