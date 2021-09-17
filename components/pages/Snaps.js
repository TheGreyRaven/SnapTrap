import { Button } from '@ui-kitten/components';
import React, { useEffect, useState } from 'react';
import { LogBox, StyleSheet, View, NativeModules } from 'react-native';
const { SnapImageModule } = NativeModules;
import FbGrid from "react-native-fb-image-grid";
import { selectDirectory } from 'react-native-directory-picker';

/*
	TODO: Create some sort of function that takes an array and return 8 images for example and on next function call add another 8 and so on.
*/

export const SnapScreen = () => {
	const [snapImages, setImages] = useState([])
	const [visible, setIsVisible] = useState(false);
	/**
	 * TODO: onPress open grid slider gallery from another library
	 */

	useEffect(() => {
		LogBox.ignoreLogs(['Animated: `useNativeDriver`']);
	}, [])

	return (
		<View style={styles.main}>
			<Button
				style={styles.btn}
				size='small'
				onPress={async () => {
					setImages([])
					const uri = await selectDirectory()
					let imgs = await SnapImageModule.getImagesFromPath(uri);

					for (let i = 0; i < imgs.length; i++) {
						setImages(snapImages => [...snapImages, imgs[i] ])
					}
					setIsVisible(true)
				}}
			>
				CHANGE SAVE FOLDER
			</Button>
			<FbGrid
            images={snapImages}
			onPress={() => console.log('got press')}
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
	},
	btn: {
		alignSelf: 'center',
		width: '50%',
		paddingHorizontal: 8,
		paddingVertical: 10,
		marginBottom: 10
	}
});