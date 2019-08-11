import React, { Component } from "react";
import IcoMoon from "react-icomoon";

class Header extends Component {
  render() {
    return (
      <header className="site-navbar py-2 bg-white" role="banner">
        <div className="container">
          <div className="row align-items-center">
            <div className="col-11 col-xl-2">
              <h1 className="mb-0 site-logo">
                <a href="/" className="text-black h2 mb-0">
                  Ticket Office
                </a>
              </h1>
            </div>
            <div className="col-12 col-md-10 d-none d-xl-block">
              <nav
                className="site-navigation position-relative text-right"
                role="navigation"
              >
                <ul className="site-menu js-clone-nav mr-auto d-none d-lg-block">
                  <li className="active">
                    <a href="/home">
                      <span>Home</span>
                    </a>
                  </li>
                  <li>
                    <a href="#.html">
                      <span>Users</span>
                    </a>
                  </li>
                  <li>
                    <a href="#.html">
                      <span>Log Out</span>
                    </a>
                  </li>
                  <li className="has-children">
                    <IcoMoon className="icon" icon="earth" />
                    <ul className="dropdown">
                      <li>
                        <a href="#">en</a>
                      </li>
                      <li>
                        <a href="#">ru</a>
                      </li>
                    </ul>
                  </li>
                </ul>
              </nav>
            </div>

            {/* <div
              className="d-inline-block d-xl-none ml-md-0 mr-auto py-3"
              //   style="position: relative; top: 3px;"
            >
              <a
                href="#"
                className="site-menu-toggle js-menu-toggle text-black"
              >
                <span className="icon-menu h3" />
              </a>
            </div> */}
          </div>
        </div>
      </header>
    );
  }
}

export default Header;
