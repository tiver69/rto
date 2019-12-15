import React, { Component } from "react";
import Ticket from "./Ticket";
import { connect } from "react-redux";
import { getPassengerPageTickets } from "../../actions/ticketActions";
import PropTypes from "prop-types";
import classnames from "classnames";
import SearchForm from "./SearchForm";

class Home extends Component {
  constructor() {
    super();
    this.state = {
      displayActive: true,
      currentPage: 1
    };
  }

  onDisplayTypeClick = e => {
    e.preventDefault();
    const { passengerId } = this.props.match.params;
    this.setState({
      displayActive: e.target.value === "true"
    });
    this.props.getPassengerPageTickets(passengerId, 0, e.target.value);
  };

  componentDidMount() {
    const { passengerId } = this.props.match.params;
    this.props.getPassengerPageTickets(
      passengerId,
      0,
      this.state.displayActive
    );
  }

  createPages = totalPages => {
    let pages = [];
    for (let i = 0; i < totalPages; i++) {
      if (i + 1 === this.state.currentPage) {
        pages.push(
          <li key={"pageNumber#" + i + 1}>
            <span>{i + 1}</span>
          </li>
        );
      } else {
        pages.push(
          <li key={"pageNumber#" + i + 1}>
            <button
              value={i}
              className="a-button"
              onClick={this.onPageClick.bind(this)}
            >
              {i + 1}
            </button>
          </li>
        );
      }
    }
    return pages;
  };

  onPageClick = number => {
    number.preventDefault();

    const { passengerId } = this.props.match.params;
    this.props.getPassengerPageTickets(
      passengerId,
      number.target.value,
      this.state.displayActive
    );

    this.setState({
      currentPage: parseInt(number.target.value) + 1
    });
  };

  render() {
    const { tickets } = this.props.ticket;
    const { passenger } = this.props.security;

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
                    <h1>Welcome, {passenger.firstName}</h1>
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
                    <div
                      className={classnames("text-center", {
                        "border-primary": this.state.displayActive
                      })}
                    >
                      <button
                        className="color-black-opacity-5 a-button"
                        onClick={this.onDisplayTypeClick.bind(this)}
                        value="true"
                      >
                        Active
                      </button>
                    </div>
                  </li>

                  <li>
                    <div
                      className={classnames("text-center", {
                        "border-primary": !this.state.displayActive
                      })}
                    >
                      <button
                        className="color-black-opacity-5 a-button"
                        onClick={this.onDisplayTypeClick.bind(this)}
                        value="false"
                      >
                        History
                      </button>
                    </div>
                  </li>
                </ul>

                {typeof tickets.currentPage !== "undefined" &&
                  tickets.currentPage.length === 0 && (
                    <div className="alert alert-secondary" role="alert">
                      Seems that you have no tickets here
                    </div>
                  )}
                {typeof tickets.currentPage !== "undefined" &&
                  tickets.currentPage.map(ticket => (
                    <Ticket
                      key={ticket.id}
                      ticket={ticket}
                      active={this.state.displayActive}
                    />
                  ))}

                {/* <!-- pages --> */}
                <div className="col-12 mt-5 text-center">
                  <ul className="custom-pagination">
                    {this.createPages(tickets.totalPages)}
                  </ul>
                </div>
              </div>

              <SearchForm history={this.props.history} />
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  }
}

Home.propTypes = {
  ticket: PropTypes.object.isRequired,
  security: PropTypes.object.isRequired,
  getPassengerPageTickets: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  security: state.security,
  ticket: state.ticket
});

export default connect(mapStateToProps, {
  getPassengerPageTickets
})(Home);
