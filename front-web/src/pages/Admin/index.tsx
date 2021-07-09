import React from "react";
import { Switch, Route } from "react-router-dom";
import Products from "./components/Products";
import SideNavbar from "./components/SideNavbar";
import "./styles.scss";

const Admin = () => (
    <div className="admin-container">
        <SideNavbar />
        <div className="admin-content">
            <Switch>
                <Route path="/admin/products">
                    <Products />
                </Route>
                <Route path="/admin/categories">
                    <h1> categories </h1>
                </Route>
                <Route path="/admin/users">
                    <h1> users </h1>
                </Route>
            </Switch>
        </div>
    </div>
);

export default Admin;
