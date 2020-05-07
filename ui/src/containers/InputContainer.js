import React, { useState } from 'react';

import InputBar from '../components/InputBar'
import TagsContainer from './TagsContainer'



function InputContainer (props) {

	const [ tags, setTags ] = useState([])

	const handleKeyPress = (event) => {
		switch (event.key) {
			case 'Enter': 	handleEnter(event); 
			// eslint-disable-next-line no-fallthrough
			case ' ': 		handleSpace(event);
			// eslint-disable-next-line no-fallthrough
			default: 		break;
		}
	}

	const handleSpace = (event) => {
		const tag = event.target.value
		if (!tags.includes(tag)) {
			setTags([...tags, tag])
		}
		event.target.value = ''
	}

	const handleError = () => {
		props.setLoading(false)
		props.setSuccess(0)
	}

	const handleSuccess = (response) => {
		response.data ? handleSaveSuccess() : handleSaveError()
	}

	const handleSaveSuccess = (response) => {
		props.setLoading(false)
		props.setSuccess(1)
	}

	const handleSaveError = (response) => {
		props.setLoading(false)
		props.setSuccess(0)
	}

	const handleChromeQuery = (url) => {
		props.setLoading(true)
		fetch('http://localhost:9090/flashcard/add', {
			method: 'POST',
			headers: {'Content-Type': 'application/json'},
			body: JSON.stringify ({
				"url": url,
				"tags": tags
			}),
		}).then(response => response.json())
		  .then((data) => handleSuccess(data), () => handleError())
		  .then(setTimeout(() => { props.setSuccess(-1) }, 1500))
	}

	const handleEnter = (event) => {
		/* eslint-disable no-undef */
		chrome.tabs.query (
			{active: true, currentWindow: true}, 
			function(tabs) { handleChromeQuery(tabs[0].url) }
		);
		/* eslint-enable no-undef */
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