import React, { Component } from "react";
import DisplayUser from "./DisplayUser";
import { getPassengers } from "../../actions/passengersActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";

class UserList extends Component {
  componentDidMount() {
    this.props.getPassengers();
  }

  render() {
    const { passengers } = this.props.passenger;

    return (
      <React.Fragment>
        <div
          className="site-blocks-cover inner-page-cover overlay background-pic"
          data-stellar-background-ratio="0.5"
        >
          <div className="container">
            <div className="row align-items-center justify-content-center text-center">
              <div className="col index-home-message user">
                <div className="row justify-content-center">
                  <div className="col text-center">
                    <h1>USERS</h1>
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
              {/* <!-- user list section --> */}
              <div className="col">
                {passengers.map(passenger => (
                  <DisplayUser passenger={passenger} key={passenger.id} />
                ))}

                {/* <!-- pages --> */}
                {/* <div className="col-12 mt-5 text-center">
              <ul className="custom-pagination">
                <c:forEach begin="1" end="${pages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li>
                                <span>${i}</span>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a href="users?currentPage=${i}" className="color-black-opacity-5">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
              </ul>
            </div> */}
              </div>
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  }
}

UserList.propTypes = {
  passenger: PropTypes.object.isRequired,
  getPassengers: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  passenger: state.passenger
});

export default connect(
  mapStateToProps,
  { getPassengers }
)(UserList);
