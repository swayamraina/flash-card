import React, { useState } from 'react';

import InputBar from '../components/InputBar'
import TagsContainer from './TagsContainer'



function InputContainer() {

	const [ tags, setTags ] = useState([])

	const handleKeyPress = (event) => {
		switch (event.key) {
			case ' ': 		handleSpace(event); break
			case 'Enter': 	handleEnter(event); break
			default:		
		}
	}

	const handleSpace = (event) => {
		const tag = event.target.value
		if (!tags.includes(tag)) {
			setTags([...tags, tag])
		}
		event.target.value = ''
	}



	const handleEnter = (event) => {
		console.log('enter')
		/* eslint-disable no-undef */
		chrome.tabs.query (
			{active: true, currentWindow: true}, 
			function(tabs) { handleChromeQuery(tabs[0].url) }
		);
		/* eslint-enable no-undef */
		event.target.value = ''
	}

	const handleClose = (index) => {
		const newTags = [...tags]
		newTags.splice(index, 1)
		setTags(newTags)
	}

	return (
		<div>
            <InputBar onKeyPress={handleKeyPress} />
			<br/>
			<TagsContainer tags={tags} onClose={handleClose} />
        </div>
	)

}

export default InputContainer;