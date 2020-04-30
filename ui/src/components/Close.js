import React from 'react';

import '../styles/Close.css'


function Close(props) {

	return (
		<span className="cross-hair">
			<button onClick={ () => props.onClose(props.index) } >X</button>
		</span>
	);
	
}

export default Close;