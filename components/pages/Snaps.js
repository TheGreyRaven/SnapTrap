import { Button } from '@ui-kitten/components';
import React, { useEffect, useState } from 'react';
import { LogBox, StyleSheet, View } from 'react-native';
import FbGrid from "react-native-fb-image-grid";
import ImageView from "react-native-image-viewing";
import { selectDirectory } from 'react-native-directory-picker';
import DefaultPreference from 'react-native-default-preference';
import { fetchImagesPath, fetchUriImagesPath } from '../libs/Images';

export const SnapScreen = () => {
	const [snapImages, setImages] = useState([])
	const [snapUriImages, setUriImages] = useState([])
	const [visible, setIsVisible] = useState(false);
	const [imgIndex, setIndex] = useState(false);

	useEffect(async () => {
		/**
		 * TODO: Clean up this mess.
		 */
		LogBox.ignoreLogs(['Animated: `useNativeDriver`']);
		if (DefaultPreference != null) {
			DefaultPreference.get('savedUri').then(async(uri) => {
				if (uri) {
					const imgs = await fetchImagesPath(uri)
					const uriImgs = await fetchUriImagesPath(uri)
					setImages(imgs)
					setUriImages(uriImgs)
				}
			})
		}
	}, [])

	return (
		<View style={styles.main}>
			<Button
				style={styles.btn}
				size='small'
				onPress={async () => {
					/**
					 * TODO: Clean up this mess.
					 */
					setImages([])
					setUriImages([])
					const uri = await selectDirectory()
					DefaultPreference.set('savedUri', uri).then(async (asd) => {
						console.log(asd)
						const uriImgs = await fetchUriImagesPath(uri)
						const imgs = await fetchImagesPath(uri)
						setImages(imgs)
						setUriImages(uriImgs)
					})
				}}
			>
				CHANGE SAVE FOLDER
			</Button>
			<FbGrid
				images={snapImages}
				onPress={(_, index) => {setIsVisible(true); setIndex(index)}}
			/>
			<ImageView
				images={snapUriImages}
				imageIndex={imgIndex}
				visible={visible}
				onRequestClose={() => setIsVisible(false)}
			/>
		</View>
	)
}

const styles = StyleSheet.create({
	main: {
		flex: 1,
		backgroundColor: '#FBFBFB',
		paddingHorizontal: 8,
		paddingVertical: 10,
		marginBottom: '10%'
	},
	btn: {
		alignSelf: 'center',
		width: '50%',
		paddingHorizontal: 8,
		paddingVertical: 10,
		marginBottom: 10
	}
});