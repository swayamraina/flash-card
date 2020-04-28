import React from 'react';

import '../styles/InputBar.css'



function InputBar(props) {

	return (
		<div>
			<input id="tag-bar" onKeyPress={props.onKeyPress} />
		</div>
	);
	
}

export default InputBar;