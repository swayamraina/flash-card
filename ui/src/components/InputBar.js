import React from 'react';

import '../styles/InputBar.css'



function InputBar(props) {

	return (
		<div className="tag-bar">
			<input onKeyPress={props.onKeyPress} />
		</div>
	);
	
}

export default InputBar;