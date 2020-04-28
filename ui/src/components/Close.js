import React from 'react';



function Close(props) {

	return (
		<span>
			<button onClick={ () => props.onClose(props.index) } > x </button>
		</span>
	);
	
}

export default Close;