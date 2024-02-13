import {createContext} from "react";
import {useSelector} from "react-redux";
import PropTypes from "prop-types";
import {AdminContextProvider} from "./AdminContext"
import {ManagerContextProvider} from "./ManagerContext"
import {VisitorContextProvider} from "./VisitorContext.jsx";
import {PersonalContextProvider} from "./PersonalContext.jsx";

export const ManageRequestContext = createContext();

export const ManageRequestContextProvider = ({children}) => {
    const role = useSelector((state) => state.role);

    switch (role) {
        case
        'ADMIN'
        :
            return <AdminContextProvider>{children}</AdminContextProvider>;
        case
        'MANAGER'
        :
            return <ManagerContextProvider>{children}</ManagerContextProvider>;
        case
        'VISITOR'
        :
            return <VisitorContextProvider>{children}</VisitorContextProvider>;
        case
        'PERSONAL'
        :
            return <PersonalContextProvider>{children}</PersonalContextProvider>;
    }
};

ManageRequestContextProvider.propTypes = {
    children: PropTypes.node.isRequired,
};