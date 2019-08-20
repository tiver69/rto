import React, { Component } from "react";
import Ticket from "./Ticket";
import { connect } from "react-redux";
import { getPassengerTickets } from "../../actions/ticketActions";
import PropTypes from "prop-types";
import classnames from "classnames";
import SearchForm from "./SearchForm";

class Home extends Component {
  constructor() {
    super();
    this.state = {
      displayActive: true
    };
  }

  onDisplayActiveClick = e => {
    this.setState({
      displayActive: true
    });
  };

  onDisplayHistoryClick = e => {
    this.setState({
      displayActive: false
    });
  };

  componentDidMount() {
    const { passengerId } = this.props.match.params;
    this.props.getPassengerTickets(passengerId);
  }

  render() {
    const { tickets } = this.props.ticket;

    const filterActiveTickets = tickets => {
      let active = [];
      const passengerTickets = tickets.map(ticket => (
        <Ticket key={ticket.id} ticket={ticket} />
      ));

      for (let i = 0; i < passengerTickets.length; i++) {
        if (passengerTickets[i].props.ticket.active.toString() === "true") {
          active.push(passengerTickets[i]);
        }
      }

      return <React.Fragment>{active}</React.Fragment>;
    };

    const filterHistoryTickets = tickets => {
      let history = [];
      const passengerTickets = tickets.map(ticket => (
        <Ticket key={ticket.id} ticket={ticket} />
      ));

      for (let i = 0; i < passengerTickets.length; i++) {
        if (passengerTickets[i].props.ticket.active.toString() === "false") {
          history.push(passengerTickets[i]);
        }
      }

      return <React.Fragment>{history}</React.Fragment>;
    };

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
                    <div
                      className={classnames("text-center", {
                        "border-primary": this.state.displayActive
                      })}
                    >
                      <button
                        className="color-black-opacity-5 a-button"
                        onClick={this.onDisplayActiveClick.bind(this)}
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
                        onClick={this.onDisplayHistoryClick.bind(this)}
                      >
                        History
                      </button>
                    </div>
                  </li>
                </ul>

                {/* {tickets.map(ticket => (
                  <Ticket key={ticket.id} ticket={ticket} />
                ))} */}

                {this.state.displayActive && filterActiveTickets(tickets)}
                {!this.state.displayActive && filterHistoryTickets(tickets)}

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
  getPassengerTickets: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
  ticket: state.ticket
});

export default connect(
  mapStateToProps,
  { getPassengerTickets }
)(Home);
