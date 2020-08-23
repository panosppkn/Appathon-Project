import React from 'react'
import ReactDOM from 'react-dom'
import axios from 'axios'
import qs from 'qs'
import {Button} from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css';

class FileInput extends React.Component {
  constructor(props) {
    // highlight-range{3}
    super(props);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.fileInput = React.createRef();
    this.state = {
        json_id: '',
        response: ''
    }
  }
  handleSubmit = event => {
    // highlight-range{3}
    event.preventDefault();
    var str = this.fileInput.current.files[0].name
    this.state.json_id = str.substring(0, str.lastIndexOf(".json"))
    console.log(this.state)
    axios
         .post('http://localhost:8080/project/add', qs.stringify(this.state))
        .then(response => {
            console.log(response)
            this.setState((state) => {
                return {json_id: state.json_id, response: response.data}
            })
        })
        .catch(error => {
            console.log(error)
        })
  }

  render() {
    // highlight-range{5}
    return (
      <form onSubmit={this.handleSubmit}>
        <label>
          <h3>Upload your json:</h3>
          <br />
          <input type="file" ref={this.fileInput} />
        </label>
        <br />
        <Button type="submit" variant="primary">Submit</Button>
        <label>{this.state.response}</label>
      </form>
    );
  }
}

ReactDOM.render(
  <FileInput />,
  document.getElementById('root')
);

export default FileInput