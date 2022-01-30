import React from "react";
import { Outlet } from "react-router-dom";
import "./Layout.css";
import Header from "./Header";

const Layout = () => {
  return (
    <React.Fragment>
      <Header />
      <main className="main">
        <Outlet />
      </main>
    </React.Fragment>
  );
};

export default Layout;
