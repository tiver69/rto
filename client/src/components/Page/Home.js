import React, { Component } from "react";
import Ticket from "../Item/Ticket";
import IcoMoon from "react-icomoon";

class Home extends Component {
  render() {
    return (
      <React.Fragment>
        {/* <!-- header --> */}
        <div
          className="site-blocks-cover inner-page-cover overlay background-pic"
          data-stellar-background-ratio="0.5"
        >
          <div className="container">
            <div className="row align-items-center justify-content-center text-center">
              <div className="col index-home-message user">
                <div className="row justify-content-center">
                  <div className="col text-center">
                    <h1>Welcome, USERNAME</h1>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        {/* <!-- main section --> */}

        <div className="site-section">
          <div className="container">
            <div className="row">
              {/* <!-- ticket list section --> */}

              <div className="col-lg-8">
                <h2 className="mb-5 text-primary-o">My ticket(s)</h2>

                <ul className="ticket-menu">
                  <li>
                    <div className="text-center border-primary">
                      <a
                        href="home?displayType=0"
                        className="color-black-opacity-5"
                      >
                        Active
                      </a>
                    </div>
                  </li>

                  <li>
                    <div className="text-center">
                      <a
                        href="home?displayType=1"
                        className="color-black-opacity-5"
                      >
                        History
                      </a>
                    </div>
                  </li>
                </ul>

                {/* <ul className="ticket-menu">
                  <li>
                    <div className="text-center">
                      <a href="home?displayType=0" className="color-black-opacity-5"><fmt:message key="user.home.active"/></a>
                    </div>
                  </li>

                  <li>
                    <div className="text-center border-primary">
                       <a href="home?displayType=1" className="color-black-opacity-5"><fmt:message key="user.home.history"/></a>
                    </div>
                  </li>
                </ul> */}

                <Ticket />

                {/* <!-- pages --> */}
                <div className="col-12 mt-5 text-center">
                  <ul className="custom-pagination">
                    <li>
                      <span>1</span>
                    </li>
                    {/* <li>
                                <a href="home?displayType=${displayType}&currentPage=${i}" className="color-black-opacity-5">${i}</a>
                            </li> */}
                  </ul>
                </div>
              </div>
              {/* <!-- search form section --> */}

              <div className="col-lg-3 ml-auto">
                <div className="mb-5">
                  <h3 className="h5 text-black mb-3">
                    Booking tickets{" "}
                    <a className="f-right p-color">
                      <IcoMoon icon="tab" />
                    </a>
                  </h3>

                  <form action="findTrain" method="get">
                    <IcoMoon icon="location" /> Departure station
                    <div className="form-group">
                      <div className="select-wrap">
                        <span className="icon">
                          <IcoMoon icon="menu" />
                        </span>
                        <select
                          className="form-control"
                          name="departureStation"
                          id="departureStationSelect"
                        >
                          <option value="${station.getId()}">Station1</option>
                        </select>
                      </div>
                    </div>
                    <IcoMoon icon="location" /> Destination station
                    <div className="form-group">
                      <div className="select-wrap">
                        <span className="icon">
                          <IcoMoon icon="menu" />
                        </span>
                        <select
                          className="form-control"
                          name="destinationStation"
                          id="destinationStationSelect"
                        >
                          <option value="${station.getId()}">Station1</option>
                        </select>
                      </div>
                    </div>
                    <div className="form-group">
                      Date:{" "}
                      <input
                        type="date"
                        name="departureDate"
                        required="required"
                        className="form-control"
                        id="dateInput"
                      />
                    </div>
                    <div className="form-group">
                      <input
                        type="submit"
                        value="Search and Order"
                        className="btn btn-primary-o"
                      />
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  }
}

export default Home;
