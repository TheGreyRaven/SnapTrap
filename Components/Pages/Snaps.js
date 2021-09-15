import { Button } from '@ui-kitten/components';
import React, { useEffect, useState } from 'react';
import { LogBox, StyleSheet, View, NativeModules } from 'react-native';
const { SnapImageModule } = NativeModules;
import ImageLayout from "react-native-image-layout";
import { selectDirectory } from 'react-native-directory-picker';

/*
	TODO: Create some sort of function that takes an array and return 8 images for example and on next function call add another 8 and so on.
*/

export const SnapScreen = () => {
	const [snapImages, setImages] = useState([])
	const [doRerender, setRerender] = useState(false)

	useEffect(() => {
		LogBox.ignoreLogs(['Animated: `useNativeDriver`']);
	}, [])

	return (
		<View style={styles.main}>
			<Button
				style={styles.btn}
				size='small'
				onPress={async () => {
					const uri = await selectDirectory()
					let imgs = await SnapImageModule.getImagesFromPath(uri);

					for (let i = 0; i < imgs.length; i++) {
						setImages(snapImages => [...snapImages, { uri: imgs[i] }])
					}
					setRerender(true)
				}}
			>
				CHANGE SAVE FOLDER
			</Button>
			<ImageLayout
				enableModal={true}
				spacing={2}
				imageContainerStyle={{ borderRadius: 10 }}
				images={snapImages}
				rerender={doRerender}
				backgroundColor={'#FBFBFB'}
				//onEndReached={}
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