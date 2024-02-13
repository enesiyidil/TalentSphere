import {ComponentPreview, Previews} from '@react-buddy/ide-toolbox'
import {PaletteTree} from './palette'
import {ApiContextProvider} from "../context/ApiContext.jsx";

const ComponentPreviews = () => {
    return (
        <Previews palette={<PaletteTree/>}>
            <ComponentPreview path="/PaletteTree">
                <PaletteTree/>
            </ComponentPreview>
            <ComponentPreview path="/ApiContextProvider">
                <ApiContextProvider/>
            </ComponentPreview>
        </Previews>
    )
}

export default ComponentPreviews