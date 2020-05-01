import React from 'react';

import '../styles/Close.css'


function Close(props) {

	return (
		<button className="cross-hair" onClick={ () => props.onClose(props.index) } >X</button>
	);
	
}

export default Close;